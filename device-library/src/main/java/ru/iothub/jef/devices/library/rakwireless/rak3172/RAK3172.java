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

import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.serial.SerialPort;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

@SuppressWarnings("unused")
public class RAK3172 {
    private final SerialPort port;
    private final InputStreamReader br;
    private final OutputStreamWriter bw;
    private final Rak3172ATCommands commands;
    private final LoRaWANDeviceConfiguration configuration;

    private DeviceInformation deviceInformation;

    public RAK3172(SerialPort port, LoRaWANDeviceConfiguration configuration) throws IOException {
        this.port = Objects.requireNonNull(port);
        this.configuration = Objects.requireNonNull(configuration);
        this.br = new InputStreamReader(port.getInputStream());
        this.bw = new OutputStreamWriter(port.getOutputStream());
        commands = new Rak3172ATCommands(this);
        //deviceInformation = new DeviceInformation(this);
        //configuration.apply(this);
    }

    private static String toHexString(byte[] payload) {
        StringBuilder hexStringBuffer = new StringBuilder();

        char[] hexDigits = new char[2];

        for (byte b : payload) {
            hexDigits[0] = Character.forDigit((b >> 4) & 0xF, 16);
            hexDigits[1] = Character.forDigit((b & 0xF), 16);
            hexStringBuffer.append(hexDigits);
        }
        return hexStringBuffer.toString();
    }

    public DeviceInformation getDeviceInformation() {
        return deviceInformation;
    }

    // General Commands
    public void resetMCU() throws IOException {
        commands.ATZ().run();
    }

    public void restoreDefaultParameters() throws IOException {
        commands.ATR().run();
        read();
    }

    private void read() throws IOException {
        //deviceInformation.read(this);
        configuration.read(this);
    }

    public void setCommandEcho() throws IOException {
        // TODO - need to disable
        commands.ATE().run();
    }

    public String getSerialNumber() throws IOException {
        return commands.AT_SN().get();
    }

    // Joining and Sending Data to LoRaWAN network
    public NetworkJoinMode getNetworkJoinMode() throws IOException {
        String s = commands.AT_NJM().get();
        return NetworkJoinMode.fromInteger(Integer.parseInt(s));
    }

    public void setNetworkJoinMode(NetworkJoinMode mode) throws IOException {
        commands.AT_NJM().set(mode.value);
    }

    public boolean isConfirmedPayloadMode() throws IOException {
        return Integer.parseInt(commands.AT_CFM().get()) == 1;
    }

    public void setConfirmedPayloadMode(boolean value) throws IOException {
        commands.AT_CFM().set(value ? 1 : 0);
    }

    public JoinLoRaWANParameters getJoinLoRaWANNetwork() throws IOException {
        return new JoinLoRaWANParameters(commands.AT_JOIN().get());
    }

    public void setJoinLoRaWANNetwork(JoinLoRaWANParameters parameters) throws IOException {
        commands.AT_JOIN().set(parameters.asString());
    }

    public boolean isJoinedLoRaWANNetwork() throws IOException {
        String s = commands.AT_NJS().get();
        return "1".equals(s);
    }

    public void sendLoRaWANPayload(int port, String data) throws IOException {
        commands.AT_SEND().set(port + ":" + data);
    }

    public boolean isLastLoRaWANPayloadSent() throws IOException {
        return "1".equals(commands.AT_CFS().get());
    }

    public LoRaWANPayload lastReceivedData() throws IOException {
        return new LoRaWANPayload(commands.AT_RECV().get());
    }

    public NetworkWorkMode getNetworkWorkMode() throws IOException {
        String s = commands.AT_NWM().get();
        return NetworkWorkMode.fromInteger(Integer.parseInt(s));
    }

    public void setNetworkWorkMode(NetworkWorkMode mode) throws IOException {
        commands.AT_NWM().set(mode.value);
    }

    public void lwSend(int port, boolean ask, byte[] payload) throws IOException {
        if (payload.length > 1024) {
            throw new IOException("Can't send more then 1024 bytes");
        }

        commands.AT_LPSEND().set(port + ":" + (ask ? 1 : 0) + ":" + toHexString(payload));
    }

    public void unifiedSendData(int port, boolean confirm, int numberOfRetransmissions, byte[] payload) throws IOException {
        if (port < 1 || port > 223) {
            throw new IOException("Port is out of range (1-223)");
        }

        if (numberOfRetransmissions < 0 || numberOfRetransmissions > 7) {
            throw new IOException("Port is out of range (0-7)");
        }

        commands.AT_USEND().set(port + ":" + (confirm ? 1 : 0) + ":" + numberOfRetransmissions + ":" + toHexString(payload));
    }

    public boolean isEnabled() {
        try {
            commands.AT().run();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    InputStreamReader getReader() {
        return br;
    }

    OutputStreamWriter getWriter() {
        return bw;
    }

    public Rak3172ATCommands getCommands() {
        return commands;
    }

    public FileHandle getHandle() {
        return this.port.getHandle();
    }
}
