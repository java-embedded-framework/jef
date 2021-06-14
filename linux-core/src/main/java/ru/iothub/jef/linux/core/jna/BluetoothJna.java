/*
 * Copyright (c) 2021, IOT-Hub.RU and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is dual-licensed: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License Version 3 as
 * published by the Free Software Foundation. For the terms of this
 * license, see <http://www.gnu.org/licenses/>.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License Version 3 for more details (a copy is included in the LICENSE
 * file that accompanied this code).
 *
 * You should have received a copy of the GNU Affero General Public License
 * version 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact support@iot-hub.ru or visit www.iot-hub.ru if you need
 * additional information or have any questions.
 *
 * You can be released from the requirements of the license by purchasing
 * a Java Embedded Framework Commercial License. Buying such a license is
 * mandatory as soon as you develop commercial activities involving the
 * Java Embedded Framework software without disclosing the source code of
 * your own applications.
 *
 * Please contact sales@iot-hub.ru if you have any question.
 */

package ru.iothub.jef.linux.core.jna;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.PointerByReference;
import ru.iothub.jef.linux.core.Bluetooth;
import ru.iothub.jef.linux.core.types.ByteReference;

public class BluetoothJna extends Bluetooth {
    @Override
    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public int hci_get_route(BDAddress address) {
        byte[] b = address == null ? null : address.getBytes();
        return Delegate.hci_get_route(b);
    }

    @Override
    public int hci_open_dev(int devId) {
        return Delegate.hci_open_dev(devId);
    }

    @Override
    public void hci_close_dev(int dd) {
        Delegate.hci_close_dev(dd);
    }

    @Override
    public int hci_le_set_scan_parameters(int dev_id, byte scan_type, short interval, short window, byte own_type, byte filter, int to) {
        return Delegate.hci_le_set_scan_parameters(dev_id, scan_type, interval, window, own_type, filter, to);
    }

    @Override
    public int hci_le_set_scan_enable(int dev_id, int enable, byte filter_dup, int to) {
        return Delegate.hci_le_set_scan_enable(dev_id, enable, filter_dup, to);
    }

    @Override
    public void hci_filter_set_ptype(int t, HciFilter f) {
        hci_filter ff = new hci_filter(f);
        hci_set_bit(
                (t == HCI_VENDOR_PKT) ?
                        0 :
                        (t & HCI_FLT_TYPE_BITS), Pointer.nativeValue(ff.getPointer()) + ff.offset("type_mask"));
        ff.fill(f);
    }

    @Override
    public void hci_filter_set_event(int e, HciFilter f) {
        hci_filter ff = new hci_filter(f);
        hci_set_bit((e & HCI_FLT_EVENT_BITS), Pointer.nativeValue(ff.getPointer()) + ff.offset("event_mask"));
        ff.fill(f);
    }

    private void hci_set_bit(int nr, long rawAddr) {
        Pointer addr = new Pointer(rawAddr);
        int offset = nr >> 5;
        int oldVal = addr.getInt(offset);
        oldVal |= (1 << (nr & 31));
        addr.setInt(offset, oldVal);
    }


    @Override
    public int hci_inquiry(int dev_id, int len, int max_rsp, ByteReference ref, InquiryInfo[] ii, int flags) {
        System.out.println("hci_inquiry start");
        //inquiry_info[] info = new inquiry_info[ii.length];


        ByteByReference lap = null;
        if (ref != null) {
            lap = new ByteByReference(ref.getValue());
        }

        int isize = new inquiry_info().size();
        int size = isize * max_rsp;

        //inquiry_info.ByReference obj = ;

/*        inquiry_info.ByReference[] info =
                (inquiry_info.ByReference[])new inquiry_info.ByReference().toArray(size)*/;

        //Memory mem = new Memory(size);
        // https://stackoverflow.com/questions/5314875/pointer-to-array-of-structures-as-jna-method-arguments
        //PointerByReference pref = new PointerByReference(mem);
        //inquiry_info inquiryInfo = new inquiry_info();
        PointerByReference iiRef = new PointerByReference();
        //Structure[] pref = new inquiry_info().toArray(max_rsp);

        //int result = Delegate.hci_inquiry(dev_id, len, max_rsp, lap, info, new NativeLong(flags, true));
        int result = Delegate.hci_inquiry(dev_id, len, max_rsp, lap, iiRef, new NativeLong(flags, true));

        //if(result > 0) {
            Pointer p = iiRef.getPointer();
            inquiry_info iii = new inquiry_info(p);
            inquiry_info[] arr = (inquiry_info[])iii.toArray(max_rsp);
            System.out.println("arr = " + arr);
        //}

/*        int offset = 0;
        for (int i = 0; i < result; i++) {
            inquiry_info info = new inquiry_info();
            info.useMemory(mem, offset);
            offset += isize;
        }*/

        System.out.println("hci_inquiry end: " + result);
        return result;
    }

    /**
     * struct hci_dev_req {
     * uint16_t dev_id;
     * uint32_t dev_opt;
     * };
     */
/*    @Structure.FieldOrder({"dev_id", "dev_opt"})
    public static class hci_dev_req extends Structure {
        public short dev_id;
        public int dev_opt;
    }*/

    @Structure.FieldOrder({"type_mask", "event_mask", "opcode"})
    public static class hci_filter extends Structure {
        public int type_mask;
        public int[] event_mask = new int[2];
        public short opcode;

        public hci_filter(HciFilter optval) {
            type_mask = optval.getTypeMask();
            event_mask = optval.getEventMask();
            opcode = optval.getOptcode();
        }

        public void fill(HciFilter optval) {
            optval.setTypeMask(type_mask);
            optval.setEventMask(event_mask);
            optval.setOptcode(opcode);
        }

        public int offset(String name) {
            return fieldOffset(name);
        }
    }

    @Structure.FieldOrder({"bdaddr", "pscan_rep_mode", "pscan_period_mode", "pscan_mode", "dev_class", "clock_offset"})
    public static class inquiry_info extends Structure implements Structure.ByValue, Structure.ByReference {
        public byte[] bdaddr = new byte[6];
        public byte pscan_rep_mode;
        public byte pscan_period_mode;
        public byte pscan_mode;
        public byte[] dev_class = new byte[3];
        public short clock_offset;

        public inquiry_info() {
        }

        public inquiry_info(Pointer p) {
            super(p);
        }

        /*
                        @Override
                        protected void useMemory(Pointer m) {
                            super.useMemory(m);
                        }

                        @Override
                        protected void useMemory(Pointer m, int offset) {
                            super.useMemory(m, offset);
                        }


                */
        public static class ByReference extends inquiry_info implements Structure.ByReference {
            public ByReference() {
            }

            public ByReference(Pointer p) { super(p); }
        }

        ;
        //public static class ByValue extends inquiry_info implements Structure.ByValue {};
    }

    static class Delegate {
        static {
            Native.register("bluetooth");
        }

        public static native int hci_get_route(byte[] ptr);

        public static native int hci_open_dev(int devId);

        //public static native int hci_inquiry(int dev_id, int len, int max_rsp, ByteByReference lap, inquiry_info.ByReference[] ii, NativeLong flags);
        public static native int hci_inquiry(int dev_id, int len, int max_rsp, ByteByReference lap, PointerByReference ii, NativeLong flags);

        public static native void hci_close_dev(int dd);

        public static native int hci_le_set_scan_parameters(int dev_id, byte scan_type, short interval, short window, byte own_type, byte filter, int to);

        public static native int hci_le_set_scan_enable(int dev_id, int enable, byte filter_dup, int to);

        //public static native void hci_filter_set_event(int e, hci_filter f);
    }
}
