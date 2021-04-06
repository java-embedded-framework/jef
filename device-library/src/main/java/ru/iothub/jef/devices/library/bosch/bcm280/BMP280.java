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

package ru.iothub.jef.devices.library.bosch.bcm280;

import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.linux.i2c.SMBus;

import java.io.IOException;

import static ru.iothub.jef.devices.library.bosch.bcm280.Register.*;

/**
 * This class provide API to access to Bosch BMP280 sensor over I2C protocol.
 * Datasheet for this sensor available at <a href="https://www.bosch-sensortec.com/media/boschsensortec/downloads/datasheets/bst-bmp280-ds001.pdf">here</a>
 */
@SuppressWarnings("unused")
public class BMP280 {
    private final static int MEAN_SEA_LEVEL_PRESSURE = 1013;

    private final static int CHIP_ID = 0x58;

    private final SMBus smbus;
    private final BMP280CalibrationData calibrationData;

    /**
     * Allocate new instance of BMP280 sensor
     * @param bus connected {@link I2CBus}
     * @param address specified {@link I2CAddress}
     * @throws IOException if device ont available in bus or bus return wrong chip ID
     */
    public BMP280(I2CBus bus, I2CAddress address) throws IOException {
        smbus = bus.select(address.getValue()).getSmBus();
        int chipID = getChipId();
        if (CHIP_ID != chipID) {
            throw new IOException("Unknown chipset version: " + chipID);
        }
        calibrationData = getCalibrationData();
    }

    /**
     * Reset sensor over power-on-reset procedure
     * @throws IOException if i2c bus not allow this operation
     */
    public void reset() throws IOException {
        smbus.writeByteData(BMP280_REGISTER_SOFTRESET.getValue(), 0xB6);
    }

    /**
     * Returns chip identification number. This number can be read as soon as the device finished the power-on-reset.
     * @return chip identification number
     * @throws IOException if i2c bus not allow this operation
     */
    public int getChipId() throws IOException {
        return smbus.readByteData(BMP280_REGISTER_CHIPID.getValue());
    }

    /**
     * Returns current power mode for sensor
     * @return current {@link PowerMode} value
     * @throws IOException if i2c bus not allow this operation
     */
    public PowerMode getPowerMode() throws IOException {
        return PowerMode.fromInteger(
                getControl() & 0b00000011
        );
    }

    /**
     * Sets power mode for sensor
     * @param mode new {@link PowerMode}
     * @throws IOException if i2c bus not allow this operation
     */
    public void setPowerMode(PowerMode mode) throws IOException {
        int curentMode = getControl() & 0b11111100;
        setControl(curentMode | mode.getValue());
    }

    /**
     * Gets temperature oversampling value. Please see {@link TemperatureOversampling} for details
     * @return current temperature oversampling value.
     * @throws IOException if i2c bus not allow this operation
     */
    public TemperatureOversampling getTemperatureOversampling() throws IOException {
        return TemperatureOversampling.fromInteger(
                (getControl() & 0b11100000) >> 5
        );
    }

    /**
     * Sets temperature oversampling value. Please see {@link TemperatureOversampling} for details
     * @param to new value
     * @throws IOException if i2c bus not allow this operation
     */
    public void setTemperatureOversampling(TemperatureOversampling to) throws IOException {
        int curentOversampling = getControl() & 0b00011111;
        setControl(curentOversampling | (to.getValue() << 5));
    }

    /**
     * Gets pressure oversampling value. Please see {@link PressureOversampling} for details
     * @return current temperature oversampling value.
     * @throws IOException if i2c bus not allow this operation
     */
    public PressureOversampling getPressureOversampling() throws IOException {
        return PressureOversampling.fromInteger(
                (getControl() & 0b00011100) >> 2
        );
    }

    /**
     * Sets pressure oversampling value. Please see {@link PressureOversampling} for details
     * @param oversampling new value
     * @throws IOException if i2c bus not allow this operation
     */
    public void setPressureOversampling(PressureOversampling oversampling) throws IOException {
        int curentOversampling = getControl() & 0b11100011;
        setControl(curentOversampling | (oversampling.getValue() << 2));
    }

    /**
     * Gets current {@link IrrFilter} value
     * @return IRR filter value
     * @throws IOException if i2c bus not allow this operation
     */
    public IrrFilter getIrrFilter() throws IOException {
        return IrrFilter.fromInteger(
                (getConfig() & 0b00011100) >> 2
        );
    }

    /**
     * Sets new {@link IrrFilter} value
     * @param irrFilter new value
     * @throws IOException if i2c bus not allow this operation
     */
    public void setIrrFilter(IrrFilter irrFilter) throws IOException {
        int config = getConfig() & 0b11100011;
        setConfig(config | (irrFilter.getValue() << 2));
    }

    /**
     * Gets current {@link StandbyTime} from sensor
     * @return current standby time
     * @throws IOException if i2c bus not allow this operation
     */
    public StandbyTime getStandbyTime() throws IOException {
        return StandbyTime.fromInteger(
                (getConfig() & 0b11100000) >> 5
        );
    }

    /**
     * Sets new {@link StandbyTime} to sensor
     * @param time new value
     * @throws IOException if i2c bus not allow this operation
     */
    public void setStandbyTime(StandbyTime time) throws IOException {
        int config = getConfig() & 0b00011111;
        setConfig(config | (time.getValue() << 5));
    }

    /**
     * Gets Spi 3 wire value
     * @return {@code true} if Spi 3 wire enabled or {@code false} if otherwise
     * @throws IOException if i2c bus not allow this operation
     */
    public boolean isSpi3wEnabled() throws IOException {
        int config = getConfig();
        return (config & 1) == 1;
    }

    /**
     * Sets new Spi 3 wire value
     * @param enabled new value
     * @throws IOException if i2c bus not allow this operation
     */
    public void setSpi3w(boolean enabled) throws IOException {
        int config = getConfig();
        setConfig(
                enabled ?
                        config | 0b00000001 :
                        config & 0b00000001
        );
    }

    /**
     * Automatically set to ‘1’ when the NVM data are being copied to image registers
     * and back to ‘0’ when the copying is done. The data are copied at power-on-
     * reset and before every conversion.
     * @return current sensor value
     * @throws IOException if i2c bus not allow this operation
     */
    public int getImUpdateStatus() throws IOException {
        return getStatus() & 0b00000001;
    }

    /**
     * Automatically set to ‘1’ whenever a conversion is
     * running and back to ‘0’ when the results have been transferred to the data registers.
     * @return current sensor value
     * @throws IOException if i2c bus not allow this operation
     */
    public int getMeasuringStatus() throws IOException {
        return (getStatus() >> 3) & 0b00000001;
    }

    private int getStatus() throws IOException {
        return smbus.readByteData(BMP280_REGISTER_STATUS.getValue());
    }

    private int getControl() throws IOException {
        return smbus.readByteData(BMP280_REGISTER_CONTROL.getValue());
    }

    private int getConfig() throws IOException {
        return smbus.readByteData(BMP280_REGISTER_CONFIG.getValue());
    }

    private void setConfig(int value) throws IOException {
        smbus.writeByteData(BMP280_REGISTER_CONFIG.getValue(), value);
    }

    private void setControl(int value) throws IOException {
        smbus.writeByteData(BMP280_REGISTER_CONTROL.getValue(), value);
    }

    /**
     * Read new value from sensor to {@link BMP280Data}
     * @return new values from sensor
     * @throws IOException if i2c bus not allow this operation
     */
    public BMP280Data getBMP280Data() throws IOException {
        for(;;) {
            if(getMeasuringStatus() != 0) {
                break;
            }
        }
        BMP280RawData bmp280RawData = getRawData();

        double t_fine = (long) getTemperatureC(bmp280RawData.getTemperature());
        double tc = compensateTC(t_fine); // C
        double tf = compensateTF(tc);
        double p = compensateP(bmp280RawData.getPressure(), t_fine); // hPa
        double a = getAltitude(p); // meters
        return new BMP280Data(p, tc, tf, a);
    }

    private double compensateTF(double tc) {
        return tc * 1.8 + 32;
    }

    private double getAltitude(double pressure) {
        return 44330.0 * (1.0 - Math.pow(pressure / MEAN_SEA_LEVEL_PRESSURE, 0.190294957));
    }


    private double getTemperatureC(long adc_T) {
        double var1 = (((double) adc_T) / 16384.0 - ((double) calibrationData.getT1()) / 1024.0)
                * ((double) calibrationData.getT2());

        double var2 = ((((double) adc_T) / 131072.0 - ((double) calibrationData.getT1()) / 8192.0) *
                (((double) adc_T) / 131072.0 - ((double) calibrationData.getT1()) / 8192.0)) * ((double) calibrationData.getT3());

        return (double) (long) (var1 + var2);
    }

    private double compensateP(long adc_P, double t_fine) {
        double var1 = (t_fine / 2.0) - 64000.0;
        double var2 = var1 * var1 * ((double) calibrationData.getP6()) / 32768.0;
        var2 = var2 + var1 * ((double) calibrationData.getP5()) * 2.0;
        var2 = (var2 / 4.0) + (((double) calibrationData.getP4()) * 65536.0);
        var1 = (((double) calibrationData.getP3()) * var1 * var1 / 524288.0
                + ((double) calibrationData.getP2()) * var1) / 524288.0;
        var1 = (1.0 + var1 / 32768.0) * ((double) calibrationData.getP1());
        double p = 1048576.0 - (double) adc_P;
        p = (p - (var2 / 4096.0)) * 6250.0 / var1;
        var1 = ((double) calibrationData.getP9()) * p * p / 2147483648.0;
        var2 = p * ((double) calibrationData.getP8()) / 32768.0;
        return (p + (var1 + var2 + ((double) calibrationData.getP7())) / 16.0) / 100;
    }

    private static double compensateTC(double t_fine) {
        return t_fine / 5120.0;
    }

    private BMP280RawData getRawData() throws IOException {
        return new BMP280RawData(
                smbus.readByteData(BMP280_REGISTER_PRESSUREDATA_MSB.getValue()),
                smbus.readByteData(BMP280_REGISTER_PRESSUREDATA_LSB.getValue()),
                smbus.readByteData(BMP280_REGISTER_PRESSUREDATA_XLSB.getValue()),
                smbus.readByteData(BMP280_REGISTER_TEMPDATA_MSB.getValue()),
                smbus.readByteData(BMP280_REGISTER_TEMPDATA_LSB.getValue()),
                smbus.readByteData(BMP280_REGISTER_TEMPDATA_XLSB.getValue())
        );
    }

    private BMP280CalibrationData getCalibrationData() throws IOException {
        BMP280CalibrationData result = new BMP280CalibrationData();

        result.T1 = smbus.readWordData(BMP280_REGISTER_DIG_T1.getValue()) & 0xFFFF;

        result.T2 = smbus.readWordData(BMP280_REGISTER_DIG_T2.getValue());
        result.T3 = smbus.readWordData(BMP280_REGISTER_DIG_T3.getValue());

        result.P1 = smbus.readWordData(BMP280_REGISTER_DIG_P1.getValue()) & 0xFFFF;
        result.P2 = smbus.readWordData(BMP280_REGISTER_DIG_P2.getValue());
        result.P3 = smbus.readWordData(BMP280_REGISTER_DIG_P3.getValue());
        result.P4 = smbus.readWordData(BMP280_REGISTER_DIG_P4.getValue());
        result.P5 = smbus.readWordData(BMP280_REGISTER_DIG_P5.getValue());
        result.P6 = smbus.readWordData(BMP280_REGISTER_DIG_P6.getValue());
        result.P7 = smbus.readWordData(BMP280_REGISTER_DIG_P7.getValue());
        result.P8 = smbus.readWordData(BMP280_REGISTER_DIG_P8.getValue());
        result.P9 = smbus.readWordData(BMP280_REGISTER_DIG_P9.getValue());
        //System.out.println("BMP280CalibrationData = " + result);
        return result;
    }

    public static final class BMP280CalibrationData {
        private int T1;
        private int T2;
        private int T3;

        private int P1;
        private int P2;
        private int P3;
        private int P4;
        private int P5;
        private int P6;
        private int P7;
        private int P8;
        private int P9;

        public BMP280CalibrationData() {
        }

        public BMP280CalibrationData(int t1, int t2, int t3, int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9) {
            T1 = t1;
            T2 = t2;
            T3 = t3;
            P1 = p1;
            P2 = p2;
            P3 = p3;
            P4 = p4;
            P5 = p5;
            P6 = p6;
            P7 = p7;
            P8 = p8;
            P9 = p9;
        }

        public int getT1() {
            return T1;
        }

        public int getT2() {
            return T2;
        }

        public int getT3() {
            return T3;
        }

        public int getP1() {
            return P1;
        }

        public int getP2() {
            return P2;
        }

        public int getP3() {
            return P3;
        }

        public int getP4() {
            return P4;
        }

        public int getP5() {
            return P5;
        }

        public int getP6() {
            return P6;
        }

        public int getP7() {
            return P7;
        }

        public int getP8() {
            return P8;
        }

        public int getP9() {
            return P9;
        }

        @Override
        public String toString() {
            return "BMP280CalibrationData{" +
                    "T1=" + T1 +
                    ", T2=" + T2 +
                    ", T3=" + T3 +
                    ", P1=" + P1 +
                    ", P2=" + P2 +
                    ", P3=" + P3 +
                    ", P4=" + P4 +
                    ", P5=" + P5 +
                    ", P6=" + P6 +
                    ", P7=" + P7 +
                    ", P8=" + P8 +
                    ", P9=" + P9 +
                    '}';
        }
    }

    public static class BMP280Data {
        private final double pressure;
        private final double temperatureC;
        private final double temperatureF;
        private final double altitude;

        public BMP280Data(double pressure, double temperatureC, double temperatureF, double altitude) {
            this.pressure = pressure;
            this.temperatureC = temperatureC;
            this.temperatureF = temperatureF;
            this.altitude = altitude;
        }

        public double getPressure() {
            return pressure;
        }

        public double getTemperatureCelsius() {
            return temperatureC;
        }

        public double getTemperatureFahrenheit() {
            return temperatureF;
        }

        public double getAltitude() {
            return altitude;
        }
    }

    private final static class BMP280RawData {
        long temperature;
        long pressure;

        public BMP280RawData(int pmsb, int plsb, int pxsb, int tmsb, int tlsb, int txsb) {
            temperature = (((long) (tmsb & 0xFF) * 65536) + ((long) (tlsb & 0xFF) * 256) + (long) (txsb & 0xF0)) / 16;
            pressure = (((long) (pmsb & 0xFF) * 65536) + ((long) (plsb & 0xFF) * 256) + (long) (pxsb & 0xF0)) / 16;
        }

        public long getTemperature() {
            return temperature;
        }

        public long getPressure() {
            return pressure;
        }
    }
}
