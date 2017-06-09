package com.example.arduinosensors;

public class AlertInfo {
    private String Quarto;
    private String Paciente_ID;
    private String Maquina;
    private String MaquinaName;
    private String AlertDate;

    AlertInfo(String Quarto, String Paciente_ID, String Maquina, String MaquinaName, String AlertDate) {
        this.setQuarto(Quarto);
        this.setPaciente_ID(Paciente_ID);
        this.setMaquina(Maquina);
        this.setMaquinaName(MaquinaName);
        this.setAlertDate(AlertDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlertInfo alertInfo = (AlertInfo) o;

        if (getQuarto() != null ? !getQuarto().equals(alertInfo.getQuarto()) : alertInfo.getQuarto() != null)
            return false;
        if (getPaciente_ID() != null ? !getPaciente_ID().equals(alertInfo.getPaciente_ID()) : alertInfo.getPaciente_ID() != null)
            return false;
        return getMaquina() != null ? getMaquina().equals(alertInfo.getMaquina()) : alertInfo.getMaquina() == null;

    }

    @Override
    public String toString() {
        return "Room: " + getQuarto() +
                ", Machine: " + getMaquinaName() +
                ", Time: " + getAlertDate() + "\n";

    }


    public String getQuarto() {
        return Quarto;
    }

    public void setQuarto(String quarto) {
        Quarto = quarto;
    }

    public String getPaciente_ID() {
        return Paciente_ID;
    }

    public void setPaciente_ID(String paciente_ID) {
        Paciente_ID = paciente_ID;
    }

    public String getMaquina() {
        return Maquina;
    }

    public void setMaquina(String maquina) {
        Maquina = maquina;
    }

    public String getMaquinaName() {
        return MaquinaName;
    }

    public void setMaquinaName(String maquinaName) {
        MaquinaName = maquinaName;
    }

    public String getAlertDate() {
        return AlertDate;
    }

    public void setAlertDate(String alertDate) {
        AlertDate = alertDate;
    }
}