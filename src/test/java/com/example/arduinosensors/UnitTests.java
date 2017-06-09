package com.example.arduinosensors;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UnitTests {
    String dataForGraphic = "*98.1,101.2,102.5~";
    int arrayFinalSize=3;
    String dataForLabels = "#95,60,32,36~";
    String dataForLabels1 = "100,60,32,36~";
    String dataForLabels2 = "*a,b,32,36~";
    String dataForLabels3 = "#54,56,35,32,36~";

    @Test
    public void InputForGraphic() throws Exception {

        BluetoothActivity.ReceivedData.setData(dataForGraphic);
        assertEquals(dataForGraphic,BluetoothActivity.ReceivedData.getData().toString());
        BluetoothActivity.ReceivedData.Process();
        BluetoothActivity.ReceivedData.clearData();
        assertEquals(arrayFinalSize,BluetoothActivity.ReceivedData.getTempPPGWaveBuffer().size());
        BluetoothActivity.ReceivedData.clearPPGwaveBuffer();
        assertEquals(0,BluetoothActivity.ReceivedData.getTempPPGWaveBuffer().size());
    }
    @Test
    public void InputForLabels() throws Exception {


        BluetoothActivity.ReceivedData.setData(dataForLabels);
        assertEquals(dataForLabels,BluetoothActivity.ReceivedData.getData().toString());
        BluetoothActivity.ReceivedData.Process();
        BluetoothActivity.ReceivedData.clearData();
        assertEquals(95,Integer.parseInt(BluetoothActivity.ReceivedData.getOxygenValue()));
        assertEquals(60,Integer.parseInt(BluetoothActivity.ReceivedData.getHeartRateValue()));
        assertEquals(32,Integer.parseInt(BluetoothActivity.ReceivedData.getStressValue()));
        assertEquals(36,BluetoothActivity.ReceivedData.getTempSampleTime());
    }

    @Test
    public void CheckInvalidInputsWithoutSpecialChars() throws Exception {

        BluetoothActivity.ReceivedData.setData(dataForLabels1);
       assertEquals(dataForLabels1,BluetoothActivity.ReceivedData.getData().toString());
        BluetoothActivity.ReceivedData.Process();
        BluetoothActivity.ReceivedData.clearData();
        assertNotEquals("100",BluetoothActivity.ReceivedData.getOxygenValue());
    }

    @Test
    public void CheckInvalidInputsWithLettersforGraphic() throws Exception {

        BluetoothActivity.ReceivedData.setData(dataForLabels2);
        assertEquals(dataForLabels2,BluetoothActivity.ReceivedData.getData().toString());
        BluetoothActivity.ReceivedData.Process();
        BluetoothActivity.ReceivedData.clearData();
        assertEquals(2,BluetoothActivity.ReceivedData.getTempPPGWaveBuffer().size());
        BluetoothActivity.ReceivedData.clearPPGwaveBuffer();
    }

    @Test
    public void CheckInvalidInputsWithMoreNumbersforLabels() throws Exception {

         BluetoothActivity.ReceivedData.setData(dataForLabels3);
        assertEquals(dataForLabels3,BluetoothActivity.ReceivedData.getData().toString());
        BluetoothActivity.ReceivedData.Process();
        BluetoothActivity.ReceivedData.clearData();
        assertNotEquals("54",BluetoothActivity.ReceivedData.getOxygenValue());

    }

    @Test
    public void CheckInvalidInputsWithLettersforLabels() throws Exception {
        String dataForLabels1 = "#d,a,32,36~";
        BluetoothActivity.ReceivedData.setData(dataForLabels1);
        assertEquals(dataForLabels1,BluetoothActivity.ReceivedData.getData().toString());
        BluetoothActivity.ReceivedData.Process();
        BluetoothActivity.ReceivedData.clearData();
        assertNotEquals("d",BluetoothActivity.ReceivedData.getOxygenValue());
    }
    @Test
    public void testAlertInfoSetter() throws Exception {
        final AlertInfo alertInfo = new AlertInfo("2", "24", "3", "Perfusion", "00:43:56");
        assertEquals("2", alertInfo.getQuarto());
        assertEquals("24", alertInfo.getPaciente_ID());
        assertEquals("3", alertInfo.getMaquina());
        assertEquals("Perfusion", alertInfo.getMaquinaName());
        assertEquals("00:43:56", alertInfo.getAlertDate());
        alertInfo.setQuarto("4");
        alertInfo.setPaciente_ID("4");
        alertInfo.setMaquina("4");
        alertInfo.setMaquinaName("Incubator");
        alertInfo.setAlertDate("00:43:57");
        assertEquals("4", alertInfo.getQuarto());
        assertEquals("4", alertInfo.getPaciente_ID());
        assertEquals("4", alertInfo.getMaquina());
        assertEquals("Incubator", alertInfo.getMaquinaName());
        assertEquals("00:43:57", alertInfo.getAlertDate());

    }

    @Test
    public void testAlertTostring() throws Exception {
        final AlertInfo alertInfo = new AlertInfo("2", "24", "3", "Perfusion", "00:43:56");
        ArrayList<AlertInfo> myAlertOrderedInfo = new ArrayList<>();
        myAlertOrderedInfo.add(alertInfo);
        MainActivity.setAlertOrderedInfo(myAlertOrderedInfo);
        String myAlertOrderedInfoString = "Room: " + "2" +
                ", Machine: " + "Perfusion" +
                ", Time: " + "00:43:56" + "\n";
        assertEquals(myAlertOrderedInfoString, MainActivity.AlertOrderedInfoToString());
    }
    @Test
    public void testQuartosToString() throws Exception {
        ArrayList<String> quartos= new ArrayList<>();
        quartos.add("2");
        quartos.add("3");
        MainActivity.setQuartoToSearchAndAlerts(quartos);
        assertEquals("2, 3.", MainActivity.quartoToSearchAndAlertsToString());
    }
}