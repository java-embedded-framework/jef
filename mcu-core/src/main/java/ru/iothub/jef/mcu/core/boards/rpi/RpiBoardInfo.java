 

package ru.iothub.jef.mcu.core.boards.rpi;

// https://www.raspberrypi.org/documentation/hardware/raspberrypi/revision-codes/README.md

import ru.iothub.jef.mcu.core.boards.Board;

@SuppressWarnings("unused")
enum RpiBoardInfo {
    A_PLUS_1_1("900021", "A+", "1.1", "512MB", "Sony UK", RaspberryPiRev2Board.class),
    B_PLUS_1_2("900032", "B+", "1.2", "512MB", "Sony UK", RaspberryPiRev2Board.class),
    ZERO_1_2("900092", "Zero", "1.2", "512MB", "Sony UK", RaspberryPi40PinsBoard.class),
    ZERO_1_3("900093", "Zero", "1.3", "512MB", "Sony UK", RaspberryPi40PinsBoard.class),
    ZERO_W("9000c1", "Zero W", "1.1", "512MB", "Sony UK", RaspberryPi40PinsBoard.class),
    THREE_A_PLUS_1_0("9020e0", "3A+", "1.0", "512MB", "Sony UK", RaspberryPi40PinsBoard.class),
    ZERO_1_2E("920092", "Zero", "1.2", "512MB", "Embest", RaspberryPi40PinsBoard.class),
    ZERO_1_3E("920093", "Zero", "1.3", "512MB", "Embest", RaspberryPi40PinsBoard.class),
    CM_1_1("900061", "CM", "1.1", "512MB", "Sony UK", RaspberryPi40PinsBoard.class),
    TWO_B_1_0("a01040", "2B", "1.0", "1GB", "Sony UK", RaspberryPiRev1Board.class),
    TWO_B_1_1("a01041", "2B", "1.1", "1GB", "Sony UK", RaspberryPiRev2Board.class),
    THREE_B_1_2("a02082", "3B", "1.2", "1GB", "Sony UK", RaspberryPi40PinsBoard.class),
    CM_3_1_0("a020a0", "CM3", "1.0", "1GB", "Sony UK", RaspberryPi40PinsBoard.class),
    THREE_B_PLUS_1_3("a020d3", "3B+", "1.3", "1GB", "Sony UK", RaspberryPi40PinsBoard.class),
    TWO_B_1_2("a02042", "2B (with BCM2837)", "1.2", "1GB", "Sony UK", RaspberryPiRev2Board.class),
    TWO_B_1_1E("a21041", "2B", "1.1", "1GB", "Embest", RaspberryPiRev1Board.class),
    TWO_B_1_2E("a22042", "2B (with BCM2837)", "1.2", "1GB", "Embest", RaspberryPiRev1Board.class),
    THREE_B_1_2E("a22082", "3B", "1.2", "1GB", "Embest", RaspberryPi40PinsBoard.class),
    CM_3_1_0E("a220a0", "CM3", "1.0", "1GB", "Embest", RaspberryPi40PinsBoard.class),
    THREE_B_1_2S("a32082", "3B", "1.2", "1GB", "Sony Japan", RaspberryPi40PinsBoard.class),
    THREE_B_1_2ST("a52082", "3B", "1.2", "1GB", "Stadium", RaspberryPi40PinsBoard.class),
    THREE_B_1_3E("a22083", "3B", "1.3", "1GB", "Embest", RaspberryPi40PinsBoard.class),
    CM_3_1_0_PLUS("a02100", "CM3+", "1.0", "1GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_1_1G("a03111", "4B", "1.1", "1GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_1_2G("b03111", "4B", "1.1", "2GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_2_2G("b03112", "4B", "1.2", "2GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_4_2G("b03114", "4B", "1.4", "2GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_1_4G("c03111", "4B", "1.1", "4GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_2_4G("c03112", "4B", "1.2", "4GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_4_4G("c03114", "4B", "1.4", "4GB", "Sony UK", RaspberryPi40PinsBoard.class),
    FOUR_B_1_4_8G("d03114", "4B", "1.4", "8GB", "Sony UK", RaspberryPi40PinsBoard.class),
    PI_400_4G("c03130", "Pi 400", "1.0", "4GB", "Sony UK", RaspberryPi40PinsBoard.class);


    private final String code;
    private final String model;
    private final String revision;
    private final String ram;
    private final String manufacturer;
    private final Class<? extends RaspberryPiAbstractBoard> provider;

    RpiBoardInfo(String code, String model, String revision, String ram, String manufacturer, Class<? extends RaspberryPiAbstractBoard> provider) {
        this.code = code;
        this.model = model;
        this.revision = revision;
        this.ram = ram;
        this.manufacturer = manufacturer;
        this.provider = provider;
    }

    public String getCode() {
        return code;
    }

    public String getModel() {
        return model;
    }

    public String getRevision() {
        return revision;
    }

    public String getRam() {
        return ram;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Class<? extends RaspberryPiAbstractBoard> getProvider() {
        return provider;
    }
}
