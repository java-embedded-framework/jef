 

package ru.iothub.jef.examples.bluetooth;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.core.*;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.types.IntReference;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static ru.iothub.jef.linux.core.Bluetooth.*;
import static ru.iothub.jef.linux.core.ErrnoCode.EAGAIN;
import static ru.iothub.jef.linux.core.ErrnoCode.EINTR;
import static ru.iothub.jef.linux.core.Signal.SA_NOCLDSTOP;
import static ru.iothub.jef.linux.core.Signal.SIGINT;

public class SimpleScanExample implements Example {
    static int check_report_filter(int procedure, LeAdvertisingInfo info) {
        IntReference flags = new IntReference(0);

        /* If no discovery procedure is set, all reports are treat as valid */
        if (procedure == 0)
            return 1;

        /* Read flags AD type value from the advertising report if it exists */
        if (read_flags(flags, info.getData(), info.getLength()) == 0) {
            return 0;
        }

        switch (procedure) {
            case 'l': /* Limited Discovery Procedure */
                if ((flags.getValue() & FLAGS_LIMITED_MODE_BIT) > 0)
                    return 1;
                break;
            case 'g': /* General Discovery Procedure */
                if ((flags.getValue() & (FLAGS_LIMITED_MODE_BIT | FLAGS_GENERAL_MODE_BIT)) > 0) {
                    return 1;
                }
                break;
            default:
                System.err.println("Unknown discovery procedure");
        }

        return 0;
    }

    static int read_flags(IntReference flags, byte[] data, int size) {
        int offset = 0;

        while (offset < size) {
            int len = data[offset];
            int type;

            /* Check if it is the end of the significant part */
            if (len == 0)
                break;

            if (len + offset > size)
                break;

            type = data[offset + 1];

            if (type == FLAGS_AD_TYPE) {
                flags.setValue(data[offset + 2]);
                return 0;
            }

            offset += 1 + len;
        }

        return ErrnoCode.ENOENT.getCode();
    }

    static void eir_parse_name(ByteBuffer eir, int eir_len, byte[] buf, int buf_len) {
        int offset = 0;

        while (offset < eir_len) {
            byte field_len = eir.get(0);
            int name_len;

            /* Check for the end of EIR */
            if (field_len == 0)
                break;

            if (offset + field_len > eir_len) {
                break;
            }

            switch (eir.get(1)) {
                case EIR_NAME_SHORT:
                case EIR_NAME_COMPLETE:
                    name_len = field_len - 1;
                    if (name_len > buf_len) {
                        break;
                    }
                    //memcpy(buf, eir[2], name_len);
                    return;
            }

            offset += field_len + 1;
            eir.position(field_len + 1);
        }

        System.err.println(Arrays.toString(buf) + " (unknown)");
    }

    @Override
    public String getName() {
        return "Simple Scan";
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void execute() throws Exception {
        // https://github.com/pauloborges/bluez/blob/master/tools/hcitool.c
        System.out.println("Execute BT example");
        //hci_tool();
        incuiry();

        // https://stackoverflow.com/questions/37711776/making-bluetooth-discoverable-using-c-code


        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    private void incuiry() {
        Bluetooth bt = Bluetooth.getInstance();
        int dev_id = bt.hci_get_route();
        if (dev_id < 0) {
            System.err.println("opening BT fail devID=" + dev_id);
            System.exit(1);
        }


        int len = 8;
        int max_rsp = 255;
        InquiryInfo[] ii = new InquiryInfo[max_rsp];

        int num_resp = bt.hci_inquiry(dev_id, len, max_rsp, null, ii, IREQ_CACHE_FLUSH);
        if (num_resp < 0) {
            System.err.println("num_resp=" + num_resp);
            System.exit(1);
        }

        int sock = bt.hci_open_dev(dev_id);
        try {
            if (sock < 0) {
                System.err.println("opening Sock fail sock=" + sock);
                System.exit(1);
            }

        } finally {
            bt.hci_close_dev(sock);
        }
    }

    private void hci_tool() throws NativeIOException {
        Bluetooth bt = Bluetooth.getInstance();

        System.out.println("Execute BT Scanner");

        int dev_id = bt.hci_get_route();
        if (dev_id < 0) {
            System.err.println("opening BT fail devID=" + dev_id);
            System.exit(1);
        }

        int dd = bt.hci_open_dev(dev_id);
        try {

            byte scan_type = 0x01;
            short interval = 0x0010;
            short window = 0x0010;
            byte own_type = 0x00;
            byte filter_policy = 0x00;
            byte filter_type = 0;
            byte filter_dup = 1;

            int err = bt.hci_le_set_scan_parameters(dd, scan_type, interval, window,
                    own_type, filter_policy, 1000);

            if (err < 0) {
                System.err.println("Enable scan failed");
                System.exit(1);
            }

            err = bt.hci_le_set_scan_enable(dd, 0x01, filter_dup, 1000);
            if (err < 0) {
                System.err.println("Enable scan failed");
                System.exit(1);
            }

            System.out.println("LE Scan ...");

            err = print_advertising_devices(dd, filter_type);

            err = bt.hci_le_set_scan_enable(dd, 0x00, filter_dup, 1000);
            if (err < 0) {
                System.err.println("Disable scan failed");
                System.exit(1);
            }

        } finally {
            bt.hci_close_dev(dd);
        }
    }

    private int print_advertising_devices(int dd, byte filter_type) throws NativeIOException {
        System.out.println("print_advertising_devices dev_id = " + dd + " filter_type=" + filter_type);

        Bluetooth bt = Bluetooth.getInstance();
        Socket sock = Socket.getInstance();
        Signal signal = Signal.getInstance();

        byte[] buf = new byte[HCI_MAX_EVENT_SIZE];
        int ptr = 0;

        HciFilter nf = new HciFilter();
        HciFilter of = new HciFilter();

        // socklen_t
        int olen;
        int len;


        sock.getsockopt(dd, SOL_HCI, HCI_FILTER, of);

        bt.hci_filter_clear(nf);
        bt.hci_filter_set_ptype(HCI_EVENT_PKT, nf);
        bt.hci_filter_set_event(EVT_LE_META_EVENT, nf);

        if (sock.setsockopt(dd, SOL_HCI, HCI_FILTER, nf) < 0) {
            System.err.println("Could not set socket options");
            return -1;
        }

        IntReference signal_received = new IntReference();

        Signal.Sigaction sa = new Signal.Sigaction(signal_received::setValue, SA_NOCLDSTOP);
        signal.sigaction(SIGINT, sa, null);

        Fcntl fcntl = Fcntl.getInstance();
        FileHandle handle = new FileHandle(dd);
        Errno errno = Errno.getInstance();

        while (true) {
            EvtLeMetaEvent meta = new EvtLeMetaEvent();
            //evt_le_meta_event * meta;
            LeAdvertisingInfo info = new LeAdvertisingInfo();
            byte[] addr = new byte[18];

            while ((len = fcntl.read(handle, buf, buf.length)) < 0) {
                if (errno.ierrno() == EINTR.getCode() && signal_received.getValue() == SIGINT) {
                    len = 0;
                    break;
                }

                if (EAGAIN.getCode() == errno.ierrno() || EINTR.getCode() == errno.ierrno()) {
                    continue;
                }
                break;
            }

            //ptr = buf + (1 + HCI_EVENT_HDR_SIZE);
            //int offset = (1 + HCI_EVENT_HDR_SIZE);
            //long seek = fcntl.lseek(handle, offset, Fcntl.Whence.SEEK_CUR);
            //System.out.println("seek = " + seek);

            len -= (1 + HCI_EVENT_HDR_SIZE);

            meta.read(handle);
            //meta = ( void *)ptr;

            if (meta.getSubEvent() != 0x02) {
                break;
            }

            /* Ignoring multiple reports */
            //info = (LeAdvertisingInfo) (meta.getData() + 1);
            info.read(handle);

            if ((check_report_filter(filter_type, info) > 0)) {
                byte[] name = new byte[30];
                //ba2str(info.getBdAddress(), addr);
                System.out.println("ba2str");
                eir_parse_name(ByteBuffer.wrap(info.getData()), info.getLength(), name, name.length - 1);

                System.out.printf("%s %s\n", Arrays.toString(addr), Arrays.toString(name));
            }
        }
        sock.setsockopt(dd, SOL_HCI, HCI_FILTER, of);

        if (len < 0) {
            return -1;
        }

        return 0;
    }

    @Override
    public void showIntro() {

    }
}
