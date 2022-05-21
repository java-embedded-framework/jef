 

package ru.iothub.jef.mcu.core.boards.opi;


enum OrangePiBoardInfo {
    // https://linux-sunxi.org/GPIO
    /**
     * (position of letter in alphabet - 1) * 32 + pin number
     * E.g for PH18 this would be ( 8 - 1) * 32 + 18 = 224 + 18 = 242 (since 'h' is the 8th letter).
     */
    // PINS defenition in http://linux-sunxi.org/Category:Xunlong
    // Boards taken from https://github.com/armbian/build/tree/master/config/boards
    // Boards taken from https://github.com/orangepi-xunlong/orangepi-build/blob/main/external/config/boards/
    // Pins mapping taken from https://github.com/torvalds/linux/tree/master/drivers/pinctrl/sunxi
    // Linux doc https://www.kernel.org/doc/Documentation/pinctrl.txt
    /**/ ORANGE_PI_R1("Orange Pi R1","sun8i", "Allwinner H2+ quad core 256MB/512MB RAM SoC WiFi SPI 2xETH", Sun8i26PinBoard.class),
    /**/ ORANGE_PI_2("Orange Pi 2", "sun8i", "Allwinner H3 quad core 1GB RAM", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_LITE("Orange Pi Lite", "sun8i", "Allwinner H3 quad core 512MB RAM WiFi", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_ONE("Orange Pi One", "sun8i", "Allwinner H3 quad core 512MB RAM SoC", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_PC("Orange Pi PC", "sun8i", "Allwinner H3 quad core 1GB RAM SoC", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_PC_PLUS("Orange Pi PC +", "sun8i", "Allwinner H3 quad core 1GB RAM WiFi eMMC", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_PLUS("Orange Pi+", "sun8i", "Allwinner H3 quad core 1GB/2GB RAM WiFi eMMC", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_PLUS_2E("Orange Pi+ 2E", "sun8i", "Allwinner H3 quad core 2GB RAM WiFi GBE eMMC", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_ZERO("Orange Pi Zero", "sun8i", "Allwinner H2+ quad core 256/512MB RAM SoC WiFi SPI", Sun8i26PinBoard.class),
    /**/ ORANGE_PI_ZERO_PLUS_2("Orange Pi Zero Plus 2", "sun8i","Allwinner H3 quad core 512MB RAM SoC Wi-Fi/BT", OrangePiZero2PlusBoard.class),

    /**/ ORANGE_PI_ZERO_PLUS("Orange Pi Zero Plus", "sun50iw2", "Allwinner H5 quad core 512MB RAM SoC GBE WiFi SPI", Sun8i26PinBoard.class), // Xunlong Orange Pi Zero same pinout
    /**/ ORANGE_PI_PC_2("Orange Pi PC2", "sun50iw2", "Allwinner H5 quad core 1GB RAM SoC GBE SPI", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_PRIME("Orange Pi Prime", "sun50iw2", "Allwinner H5 quad core 2GB RAM Wi-Fi/BT", Sun8i40PinBoard.class),
    /**/ ORANGE_PI_ZERO_PLUS_2A("Orange Pi Zero Plus 2", "sun50iw2", "Allwinner H5 quad core 512MB RAM SoC Wi-Fi/BT", OrangePiZero2PlusBoard.class),

    /**/ ORANGE_PI("Orange Pi", "sun7i","Allwinner A20 dual core 1Gb SoC Wifi USB hub", OrangePiBoard.class),
    /**/ ORANGE_PI_MINI("Orange Pi Mini", "sun7i", "Allwinner A20 dual core 1Gb SoC Wifi", OrangePiMiniBoard.class),

    /**/ ORANGE_PI_3("Orange Pi 3", "sun50iw6", "Allwinner H6 quad core 2GB RAM SoC GBE USB3", OrangePi3Board.class),

    /**/ ORANGE_PI_LITE_2("Orange Pi Lite 2", "sun50iw6", "Allwinner H6 quad core 1GB RAM SoC WiFi USB3", OrangePiLight2Board.class),

    /**/ ORANGE_PI_ONE_PLUS("Orange Pi One+", "sun50iw6", "Allwinner H6 quad core 1GB RAM SoC GBE", OrangePiLight2Board.class),
    /**/ ORANGE_PI_WIN("Orange Pi Win", "sun50iw1", "Allwinner A64 quad core 1GB/2GB RAM SoC GBE WiFi/BT", OrangePiWinBoard.class),
    /**/ ORANGE_PI_ZERO_2("Orange Pi Zero2", "sun50iw6", "Allwinner H616 quad core 512MB/1GB RAM SoC WiFi SPI USB-C", OrangePiZero2Board.class),

    ORANGE_PI_R1_PLUS("Orange Pi R1 Plus", "rockchip64", "Rockchip RK3328 quad core 1GB 2xGBE USB2 SPI", null),
    // https://esys.ir/images/img_Item/1422/Files/OrangePi%20RK3399_RK3399_User%20Manual_v1.3.pdf
    ORANGE_PI_RK3399("Orange Pi RK3399", "rk3399", "Rockchip RK3399 hexa core 2G/4GB RAM SoC GBE eMMC mPCI USB3 WiFi/BT", null),
    ORANGE_PI_4("OrangePi 4", "rk3399", "Rockchip RK3399 hexa core 4GB RAM SoC GBE eMMC USB3 USB-C WiFi/BT", null);

    private final String name;
    private final String family;
    private final String description;
    private final Class<? extends OrangePiAbstractBoard> provider;

    OrangePiBoardInfo(String name, String family, String description, Class<? extends OrangePiAbstractBoard> provider) {
        this.name = name;
        this.family = family;
        this.description = description;
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends OrangePiAbstractBoard> getProvider() {
        return provider;
    }
}
