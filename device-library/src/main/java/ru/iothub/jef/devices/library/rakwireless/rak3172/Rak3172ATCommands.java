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

package ru.iothub.jef.devices.library.rakwireless.rak3172;

/*
-----------------------------------------
|		|	General Commands	|		|
|	1	|	AT	                |	+	|
|	2	|	ATZ	                |	+	|
|	3	|	ATR	                |	+	|
|	4	|	ATE	                |	+	|
|	5	|	AT+SN	            |	+	|
-----------------------------------------
|		|	Keys, IDs and EUIs management
|	6	|	AT+DEVEUI	        |	+	|
|	7	|	AT+APPEUI	        |	+	|
|	8	|	AT+APPKEY	        |	+	|
|	9	|	AT+DEVADDR	        |	+	|
|	10	|	AT+APPSKEY	        |	+	|
|	11	|	AT+NWKSKEY	        |	+	|
-----------------------------------------
|		|	Joining and Sending Data to LoRaWAN® network
|	12	|	AT+NJM	            |	+	|
|	13	|	AT+CFM	            |	+	|
|	14	|	AT+JOIN	            |	+	|
|	15	|	AT+NJS	            |	+	|
|	16	|	AT+SEND	            |	+	|
|	17	|	AT+CFS	            |	+	|
|	18	|	AT+RECV	            |	+	|
-----------------------------------------
|		|	LoRaWAN device configuration -> LoRaWANDeviceConfiguration.class
|	19	|	AT+ADR	            |	+	|
|	20	|	AT+CLASS	        |	+	|
|	21	|	AT+DCS	            |	+	|
|	22	|	AT+DUTYTIME	        |	+	|
|	23	|	AT+DR	            |	+	|
|	24	|	AT+JN1DL	        |	+	|
|	25	|	AT+JN2DL	        |	+	|
|	26	|	AT+RX1DL	        |	+	|
|	27	|	AT+RX2DL	        |	+	|
|	28	|	AT+RX2DR	        |	+	|
|	29	|	AT+RX2FQ	        |	+	|
|	30	|	AT+TXP	            |	+	|
|	31	|	AT+RETY	            |	+	|
|	32	|	AT+MASK	            |	+	|
|	33	|	AT+BAND	            |	+	|
|	34	|	AT+LPSEND	        |	+	|
|	35	|	AT+LINKCHECK	    |	+	|
|	36	|	AT+USEND	        |	+	|
-----------------------------------------
|		|	Class B Mode	    |		|
|	37	|	AT+PGSLOT	        |		|
|	38	|	AT+BFREQ	        |		|
|	39	|	AT+LTIME	        |		|
-----------------------------------------
|		|	Device information	-> DeviceInformation.class
|	40	|	AT+RSSI	            |	+	|
|	41	|	AT+SNR	            |	+	|
|	42	|	AT+VER	            |	+	|
-----------------------------------------
|		|	RF Test	            |		|
|	43	|	AT+TRSSI	        |		|
|	44	|	AT+TTONE	        |		|
|	45	|	AT+TTX	            |		|
|	46	|	AT+TRX	            |		|
|	47	|	AT+TCONF	        |		|
|	48	|	AT+TTH	            |		|
|	49	|	AT+TOFF	            |		|
|	50	|	AT+CERTIF	        |		|
-----------------------------------------
|		|	P2P Mode	        |		|
|	51	|	AT+NWM	            |	+	|
|	52	|	AT+PFREQ	        |		|
|	53	|	AT+PSF	            |		|
|	54	|	AT+PBW	            |		|
|	55	|	AT+PCR	            |		|
|	56	|	AT+PPL	            |		|
|	57	|	AT+PTP	            |		|
|	58	|	AT+P2P	            |		|
|	59	|	AT+PSEND	        |		|
|	60	|	AT+PRECV	        |		|
-----------------------------------------
|		|	Multicast Group	    |		|
|	61	|	AT+ADDMULC	        |		|
|	62	|	AT+RMVMULC	        |		|
|	63	|	AT+LSTMULC	        |		|
-----------------------------------------
|		|	Data Transparent Tranmission
|	64	|	ATD	                |		|
|	65	|	`+++	            |		|
-----------------------------------------
 */
public class Rak3172ATCommands {
    // General Commands
    private ATCommand AT;
    private ATCommand ATZ;
    private ATCommand ATR;
    private ATCommand ATE;
    private ATCommand AT_SN;

    // Keys, IDs and EUIs management
    private ATCommand AT_DEVEUI;
    private ATCommand AT_APPEUI;
    private ATCommand AT_APPKEY;
    private ATCommand AT_DEVADDR;
    private ATCommand AT_APPSKEY;
    private ATCommand AT_NWKSKEY;


    // Joining and Sending Data to LoRaWAN network
    private ATCommand AT_NJM;
    private ATCommand AT_CFM;
    private ATCommand AT_JOIN;
    private ATCommand AT_NJS;
    private ATCommand AT_SEND;
    private ATCommand AT_CFS;
    private ATCommand AT_RECV;

    // LoRaWAN device configuration
    private ATCommand AT_ADR;
    private ATCommand AT_CLASS;
    private ATCommand AT_DCS;
    private ATCommand AT_DUTYTIME;
    private ATCommand AT_DR;
    private ATCommand AT_JN1DL;
    private ATCommand AT_JN2DL;
    private ATCommand AT_RX1DL;
    private ATCommand AT_RX2DL;
    private ATCommand AT_RX2DR;
    private ATCommand AT_RX2FQ;
    private ATCommand AT_TXP;
    private ATCommand AT_RETY;
    private ATCommand AT_MASK;
    private ATCommand AT_BAND;
    private ATCommand AT_LPSEND;
    private ATCommand AT_LINKCHECK;
    private ATCommand AT_USEND;


    private ATCommand AT_VER;
    private ATCommand AT_RSSI;
    private ATCommand AT_SNR;

    private ATCommand AT_NWM;

    private final RAK3172 rak;

    public Rak3172ATCommands(RAK3172 rak) {
        this.rak = rak;
        init();
    }

    private void init() {
        // General Commands
        AT = new ATCommand(rak, "AT");
        ATZ = new ATCommand(rak, "ATZ");
        ATR = new ATCommand(rak, "ATR");
        ATE = new ATCommand(rak, "ATE");
        AT_SN = new ATCommand(rak, "AT+SN");

        // Keys, IDs and EUIs management
        AT_DEVEUI = new ATCommand(rak, "AT+DEVEUI");
        AT_APPEUI = new ATCommand(rak, "AT+APPEUI");
        AT_APPKEY = new ATCommand(rak, "AT+APPKEY");
        AT_DEVADDR = new ATCommand(rak, "AT+DEVADDR");
        AT_APPSKEY = new ATCommand(rak, "AT+APPSKEY");
        AT_NWKSKEY = new ATCommand(rak, "AT+NWKSKEY");

        // Joining and Sending Data to LoRaWAN network
        AT_NJM = new ATCommand(rak, "AT+NJM");
        AT_CFM = new ATCommand(rak, "AT+CFM");
        AT_JOIN = new ATCommand(rak, "AT+JOIN");
        AT_NJS = new ATCommand(rak, "AT+NJS");
        AT_SEND = new ATCommand(rak, "AT+SEND");
        AT_CFS = new ATCommand(rak, "AT+CFS");
        AT_RECV = new ATCommand(rak, "AT+RECV");

        // LoRaWAN device configuration
        AT_ADR = new ATCommand(rak, "AT+ADR");
        AT_CLASS = new ATCommand(rak, "AT+CLASS");
        AT_DCS = new ATCommand(rak, "AT+DCS");
        AT_DUTYTIME = new ATCommand(rak, "AT+DUTYTIME");
        AT_DR = new ATCommand(rak, "AT+DR");
        AT_JN1DL = new ATCommand(rak, "AT+JN1DL");
        AT_JN2DL = new ATCommand(rak, "AT+JN2DL");
        AT_RX1DL = new ATCommand(rak, "AT+RX1DL");
        AT_RX2DL = new ATCommand(rak, "AT+RX2DL");
        AT_RX2DR = new ATCommand(rak, "AT+RX2DR");
        AT_RX2FQ = new ATCommand(rak, "AT+RX2FQ");
        AT_TXP = new ATCommand(rak, "AT+TXP");
        AT_RETY = new ATCommand(rak, "AT+RETY");
        AT_MASK = new ATCommand(rak, "AT+MASK");
        AT_BAND = new ATCommand(rak, "AT+BAND");
        AT_LPSEND = new ATCommand(rak, "AT+LPSEND");
        AT_LINKCHECK = new ATCommand(rak, "AT+LINKCHECK");
        AT_USEND = new ATCommand(rak, "AT+USEND");


        AT_VER = new ATCommand(rak, "AT+VER");
        AT_RSSI = new ATCommand(rak, "AT+RSSI");
        AT_SNR = new ATCommand(rak, "AT+SNR");

        AT_NWM = new ATCommand(rak, "AT+NWM");
    }

    // General Commands
    public ATCommand AT() {
        return AT;
    }

    public ATCommand ATZ() {
        return ATZ;
    }

    public ATCommand ATR() {
        return ATR;
    }

    public ATCommand ATE() {
        return ATE;
    }

    public ATCommand AT_SN() {
        return AT_SN;
    }

    // Keys, IDs and EUIs management
    public ATCommand AT_DEVEUI() {
        return AT_DEVEUI;
    }

    public ATCommand AT_APPEUI() {
        return AT_APPEUI;
    }

    public ATCommand AT_APPKEY() {
        return AT_APPKEY;
    }

    public ATCommand AT_DEVADDR() {
        return AT_DEVADDR;
    }

    public ATCommand AT_APPSKEY() {
        return AT_APPSKEY;
    }

    public ATCommand AT_NWKSKEY() {
        return AT_NWKSKEY;
    }

    // Joining and Sending Data to LoRaWAN network
    public ATCommand AT_NJM() {
        return AT_NJM;
    }

    public ATCommand AT_CFM() {
        return AT_NJM;
    }

    public ATCommand AT_JOIN() {
        return AT_NJM;
    }

    public ATCommand AT_NJS() {
        return AT_NJM;
    }

    public ATCommand AT_SEND() {
        return AT_NJM;
    }

    public ATCommand AT_CFS() {
        return AT_NJM;
    }

    public ATCommand AT_RECV() {
        return AT_NJM;
    }

    // LoRaWAN device configuration
    public ATCommand AT_ADR() {
        return AT_ADR;
    }
    public ATCommand AT_CLASS() {
        return AT_CLASS;
    }
    public ATCommand AT_DCS() {
        return AT_DCS;
    }
    public ATCommand AT_DUTYTIME() {
        return AT_DUTYTIME;
    }
    public ATCommand AT_DR() {
        return AT_DR;
    }
    public ATCommand AT_JN1DL() {
        return AT_JN1DL;
    }
    public ATCommand AT_JN2DL() {
        return AT_JN2DL;
    }
    public ATCommand AT_RX1DL() {
        return AT_RX1DL;
    }
    public ATCommand AT_RX2DL() {
        return AT_RX2DL;
    }
    public ATCommand AT_RX2DR() {
        return AT_RX2DR;
    }
    public ATCommand AT_RX2FQ() {
        return AT_RX2FQ;
    }
    public ATCommand AT_TXP() {
        return AT_TXP;
    }
    public ATCommand AT_RETY() {
        return AT_RETY;
    }
    public ATCommand AT_MASK() {
        return AT_MASK;
    }
    public ATCommand AT_BAND() {
        return AT_BAND;
    }
    public ATCommand AT_LPSEND() {
        return AT_LPSEND;
    }
    public ATCommand AT_LINKCHECK() {
        return AT_LINKCHECK;
    }
    public ATCommand AT_USEND() {
        return AT_USEND;
    }



    public ATCommand AT_VER() {
        return AT_VER;
    }

    public ATCommand AT_RSSI() {
        return AT_RSSI;
    }

    public ATCommand AT_SNR() {
        return AT_SNR;
    }

    public ATCommand AT_NWM() {
        return AT_NWM;
    }
}
