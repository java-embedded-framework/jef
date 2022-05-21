 

package ru.iothub.jef.linux.core;

import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.types.ByteReference;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

// sudo apt-get install libbluetooth-dev
// https://people.csail.mit.edu/albert/bluez-intro/
// https://android.googlesource.com/platform/external/bluetooth/bluez/+/master/tools/hcitool.c
public abstract class Bluetooth implements NativeSupport {
    public static final int IREQ_CACHE_FLUSH = 0x0001;
    public static final int HCI_MAX_EVENT_SIZE = 260;
    public static final int SOL_HCI = 0;

    /* HCI Socket options */
    public static final int HCI_DATA_DIR = 1;
    public static final int HCI_FILTER = 2;
    public static final int HCI_TIME_STAMP = 3;

    /* HCI Packet types */
    public static final int HCI_COMMAND_PKT = 0x01;
    public static final int HCI_ACLDATA_PKT = 0x02;
    public static final int HCI_SCODATA_PKT = 0x03;
    public static final int HCI_EVENT_PKT = 0x04;
    public static final int HCI_VENDOR_PKT = 0xff;

    public static final int HCI_FLT_TYPE_BITS = 31;
    public static final int HCI_FLT_EVENT_BITS = 63;
    public static final int HCI_FLT_OGF_BITS = 63;
    public static final int HCI_FLT_OCF_BITS = 127;


    public static final int EVT_LE_META_EVENT = 0x3E;

    public static final int HCI_EVENT_HDR_SIZE = 2;

    public static final int FLAGS_AD_TYPE = 0x01;
    public static final int FLAGS_LIMITED_MODE_BIT = 0x01;
    public static final int FLAGS_GENERAL_MODE_BIT = 0x02;


    /* Extended Inquiry Response field types */
    public static final int EIR_FLAGS = 0x01; /* flags */
    public static final int EIR_UUID16_SOME = 0x02; /* 16-bit UUID, more available */
    public static final int EIR_UUID16_ALL = 0x03; /* 16-bit UUID, all listed */
    public static final int EIR_UUID32_SOME = 0x04; /* 32-bit UUID, more available */
    public static final int EIR_UUID32_ALL = 0x05; /* 32-bit UUID, all listed */
    public static final int EIR_UUID128_SOME = 0x06; /* 128-bit UUID, more available */
    public static final int EIR_UUID128_ALL = 0x07; /* 128-bit UUID, all listed */
    public static final int EIR_NAME_SHORT = 0x08; /* shortened local name */
    public static final int EIR_NAME_COMPLETE = 0x09; /* complete local name */
    public static final int EIR_TX_POWER = 0x0A; /* transmit power level */
    public static final int EIR_CLASS_OF_DEV = 0x0D; /* Class of Device */
    public static final int EIR_SSP_HASH_C192 = 0x0E; /* Simple Pairing Hash C-192 */
    public static final int EIR_SSP_RAND_R192 = 0x0F; /* Simple Pairing Randomizer R-192 */
    public static final int EIR_DEVICE_ID = 0x10; /* device ID */
    public static final int EIR_APPEARANCE = 0x19; /* Device appearance */
    public static final int EIR_LE_BDADDR = 0x1B; /* LE Bluetooth device address */
    public static final int EIR_LE_ROLE = 0x1C; /* LE role */
    public static final int EIR_SSP_HASH_C256 = 0x1D; /* Simple Pairing Hash C-256 */
    public static final int EIR_SSP_RAND_R256 = 0x1E; /* Simple Pairing Rand R-256 */
    public static final int EIR_LE_SC_CONFIRM = 0x22;/* LE SC Confirmation Value */
    public static final int EIR_LE_SC_RANDOM = 0x23;/* LE SC Random Value */


/*
ba2oui
ba2str
bachk
bafprintf
baprintf
basnprintf
basprintf
baswap
batostr
bt_compidtostr
bt_error
bt_free
bt_malloc
hci_authenticate_link
hci_bustostr
hci_change_link_key
hci_close_dev
hci_cmdtostr
hci_commandstostr
hci_create_connection
hci_delete_stored_link_key
hci_devba
hci_devid
hci_devinfo
hci_dflagstostr
hci_disconnect
hci_dtypetostr
hci_encrypt_link
hci_exit_park_mode
hci_for_each_dev
hci_get_route
hci_inquiry
hci_le_add_resolving_list
hci_le_add_white_list
hci_le_clear_resolving_list
hci_le_clear_white_list
hci_le_conn_update
hci_le_create_conn
hci_le_read_remote_features
hci_le_read_resolving_list_size
hci_le_read_white_list_size
hci_le_rm_resolving_list
hci_le_rm_white_list
hci_le_set_address_resolution_enable
hci_le_set_advertise_enable
hci_le_set_scan_enable
hci_le_set_scan_parameters
hci_lmtostr
hci_lptostr
hci_open_dev
hci_park_mode
hci_ptypetostr
hci_read_afh_map
hci_read_afh_mode
hci_read_bd_addr
hci_read_class_of_dev
hci_read_clock
hci_read_clock_offset
hci_read_current_iac_lap
hci_read_ext_inquiry_response
hci_read_inq_response_tx_power_level
hci_read_inquiry_mode
hci_read_inquiry_scan_type
hci_read_inquiry_transmit_power_level
hci_read_link_policy
hci_read_link_quality
hci_read_link_supervision_timeout
hci_read_local_commands
hci_read_local_ext_features
hci_read_local_features
hci_read_local_name
hci_read_local_oob_data
hci_read_local_version
hci_read_remote_ext_features
hci_read_remote_features
hci_read_remote_name
hci_read_remote_name_cancel
hci_read_remote_name_with_clock_offset
hci_read_remote_version
hci_read_rssi
hci_read_simple_pairing_mode
hci_read_stored_link_key
hci_read_transmit_power_level
hci_read_voice_setting
hci_scoptypetostr
hci_send_cmd
hci_send_req
hci_set_afh_classification
hci_strtolm
hci_strtolp
hci_strtoptype
hci_strtoscoptype
hci_strtover
hci_switch_role
hci_typetostr
hci_vertostr
hci_write_afh_mode
hci_write_class_of_dev
hci_write_current_iac_lap
hci_write_ext_inquiry_response
hci_write_inquiry_mode
hci_write_inquiry_scan_type
hci_write_inquiry_transmit_power_level
hci_write_link_policy
hci_write_link_supervision_timeout
hci_write_local_name
hci_write_simple_pairing_mode
hci_write_stored_link_key
hci_write_voice_setting
lmp_featurestostr
lmp_strtover
lmp_vertostr
pal_strtover
pal_vertostr
sdp_add_lang_attr
sdp_append_to_buf
sdp_append_to_pdu
sdp_attr_add
sdp_attr_add_new
sdp_attrid_comp_func
sdp_attr_remove
sdp_attr_replace
sdp_close
sdp_connect
sdp_copy_record
sdp_create
sdp_data_alloc
sdp_data_alloc_with_length
sdp_data_free
sdp_data_get
sdp_device_record_register
sdp_device_record_register_binary
sdp_device_record_unregister
sdp_device_record_unregister_binary
sdp_device_record_update
sdp_device_record_update_binary
sdp_extract_attr
sdp_extract_pdu
sdp_extract_seqtype
sdp_general_inquiry
sdp_gen_pdu
sdp_gen_record_pdu
sdp_gen_tid
sdp_get_access_protos
sdp_get_add_access_protos
sdp_get_database_state
sdp_get_error
sdp_get_group_id
sdp_get_int_attr
sdp_get_lang_attr
sdp_get_profile_descs
sdp_get_proto_desc
sdp_get_proto_port
sdp_get_record_state
sdp_get_server_ver
sdp_get_service_avail
sdp_get_service_id
sdp_get_service_ttl
sdp_get_socket
sdp_get_string_attr
sdp_get_supp_feat
sdp_get_uuidseq_attr
sdp_list_append
sdp_list_free
sdp_list_insert_sorted
sdp_list_remove
sdp_pattern_add_uuid
sdp_pattern_add_uuidseq
sdp_process
sdp_profile_uuid2strn
sdp_proto_uuid2strn
sdp_record_alloc
sdp_record_free
sdp_record_print
sdp_record_register
sdp_record_unregister
sdp_record_update
sdp_send_req_w4_rsp
sdp_seq_alloc
sdp_seq_alloc_with_length
sdp_seq_append
sdp_service_attr_async
sdp_service_attr_req
sdp_service_search_async
sdp_service_search_attr_async
sdp_service_search_attr_req
sdp_service_search_req
sdp_set_access_protos
sdp_set_add_access_protos
sdp_set_attrid
sdp_set_group_id
sdp_set_info_attr
sdp_set_lang_attr
sdp_set_notify
sdp_set_profile_descs
sdp_set_seq_len
sdp_set_service_id
sdp_set_supp_feat
sdp_set_url_attr
sdp_set_uuidseq_attr
sdp_svclass_uuid2strn
sdp_uuid128_cmp
sdp_uuid128_create
sdp_uuid128_to_uuid
sdp_uuid16_cmp
sdp_uuid16_create
sdp_uuid16_to_uuid128
sdp_uuid2strn
sdp_uuid32_create
sdp_uuid32_to_uuid128
sdp_uuid_cmp
sdp_uuid_extract
sdp_uuid_to_proto
sdp_uuid_to_uuid128
str2ba
strtoba
     */

    /*
    -- defined hci.h
    public static final int  EVT_INQUIRY_RESULT		0x02
    typedef struct {
        bdaddr_t	bdaddr;
        uint8_t		pscan_rep_mode;
        uint8_t		pscan_period_mode;
        uint8_t		pscan_mode;
        uint8_t		dev_class[3];
        uint16_t	clock_offset;
        } __attribute__ ((packed)) inquiry_info;
    public static final int  INQUIRY_INFO_SIZE 14
    */

    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Bluetooth instance = null;

    public static Bluetooth getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Bluetooth.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Bluetooth.class);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }

    public int hci_get_route() {
        return hci_get_route(null);
    }

    public abstract int hci_get_route(BDAddress address);

    public abstract int hci_open_dev(int devId);

    public abstract int hci_inquiry(int dev_id, int len, int max_rsp, ByteReference byteReference, InquiryInfo[] ii, int flags);

    public abstract void hci_close_dev(int dd);

    public abstract int hci_le_set_scan_parameters(int dev_id, byte scan_type, short interval, short window, byte own_type, byte filter, int to);

    public abstract int hci_le_set_scan_enable(int dev_id, int enable, byte filter_dup, int to);

    public void hci_filter_clear(HciFilter f) {
    }

    public abstract void hci_filter_set_ptype(int t, HciFilter f);

    public abstract void hci_filter_set_event(int e, HciFilter f);


    public static class BDAddress {
        private byte[] bytes = new byte[6];

        public byte[] getBytes() {
            return bytes;
        }
    }

    public static class InquiryInfo {
        public byte[] bdaddr = new byte[6];
        byte pscan_rep_mode;
        byte pscan_period_mode;
        byte pscan_mode;
        byte[] dev_class = new byte[3];
        short clock_offset;

        public byte[] getBdaddr() {
            return bdaddr;
        }

        public void setBdaddr(byte[] bdaddr) {
            this.bdaddr = bdaddr;
        }

        public byte getPscan_rep_mode() {
            return pscan_rep_mode;
        }

        public void setPscan_rep_mode(byte pscan_rep_mode) {
            this.pscan_rep_mode = pscan_rep_mode;
        }

        public byte getPscan_period_mode() {
            return pscan_period_mode;
        }

        public void setPscan_period_mode(byte pscan_period_mode) {
            this.pscan_period_mode = pscan_period_mode;
        }

        public byte getPscan_mode() {
            return pscan_mode;
        }

        public void setPscan_mode(byte pscan_mode) {
            this.pscan_mode = pscan_mode;
        }

        public byte[] getDev_class() {
            return dev_class;
        }

        public void setDev_class(byte[] dev_class) {
            this.dev_class = dev_class;
        }

        public short getClock_offset() {
            return clock_offset;
        }

        public void setClock_offset(short clock_offset) {
            this.clock_offset = clock_offset;
        }
    }

    /*
    struct hci_filter {
        uint32_t type_mask;
        uint32_t event_mask[2];
        uint16_t opcode;
    };

    */
    public static class HciFilter {
        private int typeMask;
        private int[] eventMask = new int[2];
        private short optcode;

        public int getTypeMask() {
            return typeMask;
        }

        public void setTypeMask(int typeMask) {
            this.typeMask = typeMask;
        }

        public int[] getEventMask() {
            return eventMask;
        }

        public void setEventMask(int[] eventMask) {
            this.eventMask = eventMask;
        }

        public short getOptcode() {
            return optcode;
        }

        public void setOptcode(short optcode) {
            this.optcode = optcode;
        }
    }

    /*
    public static final int  EVT_LE_META_EVENT	0x3E
        typedef struct {
            uint8_t		subevent;
            uint8_t		data[0];
        } __attribute__ ((packed)) evt_le_meta_event;
    public static final int  EVT_LE_META_EVENT_SIZE 1
    */

    public static class EvtLeMetaEvent {
        byte subEvent;
        byte data;

        public byte getSubEvent() {
            return subEvent;
        }

        public void setSubEvent(byte subEvent) {
            this.subEvent = subEvent;
        }

        public byte getData() {
            return data;
        }

        public void setData(byte data) {
            this.data = data;
        }

        public void read(FileHandle handle) throws NativeIOException {
            Fcntl fcntl = Fcntl.getInstance();
            byte[] b = new byte[2];
            fcntl.read(handle, b, 2);
            subEvent = b[0];
            data = b[1];
        }
    }

    /*
     public static final int  EVT_LE_ADVERTISING_REPORT	0x02
        typedef struct {
            uint8_t		evt_type;
            uint8_t		bdaddr_type;
            bdaddr_t	bdaddr;
            uint8_t		length;
            uint8_t		data[0];
        } __attribute__ ((packed)) le_advertising_info;
    public static final int  LE_ADVERTISING_INFO_SIZE 9
     */

    public static class LeAdvertisingInfo {
        byte eventType;
        byte bdAddressType;
        BDAddress bdAddress;
        byte length;
        byte[] data = new byte[0];

        public byte getEventType() {
            return eventType;
        }

        public void setEventType(byte eventType) {
            this.eventType = eventType;
        }

        public byte getBdAddressType() {
            return bdAddressType;
        }

        public void setBdAddressType(byte bdAddressType) {
            this.bdAddressType = bdAddressType;
        }

        public BDAddress getBdAddress() {
            return bdAddress;
        }

        public void setBdAddress(BDAddress bdAddress) {
            this.bdAddress = bdAddress;
        }

        public byte getLength() {
            return length;
        }

        public void setLength(byte length) {
            this.length = length;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public void read(FileHandle handle) throws NativeIOException {
            Fcntl fcntl = Fcntl.getInstance();
            byte[] b = new byte[10];
            fcntl.read(handle, b, b.length);
            ByteBuffer buf = ByteBuffer.wrap(b);
            eventType = buf.get();
            bdAddressType = buf.get();
            buf.get(bdAddress.bytes, buf.position(), 6);
            length = buf.get();
            //data = buf.get();
        }
    }
}
