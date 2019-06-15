package pe.edu.cibertec.laboaudio_video;

public class Audio {
    String ruta;


    public Audio(String fileName)
    {
        this.ruta = fileName;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
