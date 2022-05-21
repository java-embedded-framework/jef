 

package ru.iothub.jef.devices.library.winbond.w25x;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides information about flash chipset based on JedecId
 */
@SuppressWarnings("unused")
enum W25xFlashSizeInfo {
    SIZE_512_KB(0x10,256, 16, 1),
    SIZE_1_MB(0x11,512, 32, 2),
    SIZE_2_MB(0x12,1024, 64, 4),
    SIZE_4_MB(0x13,2048, 128, 8),
    SIZE_8_MB(0x14,4096, 256, 16),
    SIZE_16_MB(0x15,8192, 512, 32),
    SIZE_32_MB(0x16,16384, 1024, 64),
    SIZE_64_MB(0x17,32768, 2048, 128),
    SIZE_128_MB(0x18,65535, 4096, 256),
    SIZE_256_MB(0x19,131072, 8192, 512),
    SIZE_512_MB(0x20,262144, 16384, 1028),
    SIZE_1_GB(0x21,524288, 32768, 2048);

    private final static int BYTES_PER_PAGE = 0xFF;
    private static final Map<Integer,W25xFlashSizeInfo> map;

    static {
        map = new HashMap<>();
        for (W25xFlashSizeInfo v : W25xFlashSizeInfo.values()) {
            map.put(v.code, v);
        }
    }

    int code;
    int pages;
    int sectors;
    int blocks64;

    /**
     * Allocate instance
     * @param code JedecId - code
     * @param pages amount of pages
     * @param sectors amount of sectors
     * @param blocks64 amount of 64k blocks
     */
    W25xFlashSizeInfo(int code, int pages, int sectors, int blocks64) {
        this.code = code;
        this.pages = pages;
        this.sectors = sectors;
        this.blocks64 = blocks64;
    }

    /**
     * Returns about of bytes per page
     * @return bytes per page
     */
    public int getBytesPerPage() {
        return BYTES_PER_PAGE;
    }

    /**
     * Returns JedecId code
     * @return code value
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns amount of pages
     * @return amount of pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * Returns amount of sectors
     * @return amount of sectors
     */
    public int getSectors() {
        return sectors;
    }

    /**
     * Returns amount of 64k blocks
     * @return amount of 64k blocks
     */
    public int getBlocks64() {
        return blocks64;
    }

    static W25xFlashSizeInfo findByKey(int i) {
        return map.get(i);
    }
}
