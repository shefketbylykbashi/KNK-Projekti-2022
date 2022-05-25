package modelAndRepository;

public class MedicamentSearchResult
{

    private String name;
    private String form;
    private String dosage;
    private boolean morning;
    private boolean noon;
    private boolean evening;

    public MedicamentSearchResult(String name, String form, String dosage, boolean morning, boolean noon, boolean evening) {
        this.name = name;
        this.form = form;
        this.dosage = dosage;
        this.morning = morning;
        this.noon = noon;
        this.evening = evening;
    }





    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public String getDosage() {
        return dosage;
    }

    public boolean isMorning() {
        return morning;
    }

    @Override
    public String toString() {
        return "MedicamentSearchResult{" +
                "name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", dosage='" + dosage + '\'' +
                ", morning=" + morning +
                ", noon=" + noon +
                ", evening=" + evening +
                '}';
    }

    public boolean isNoon() {
        return noon;
    }

    public boolean isEvening() {
        return evening;
    }
}
