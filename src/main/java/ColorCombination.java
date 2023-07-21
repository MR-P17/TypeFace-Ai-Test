public class ColorCombination {
    private String color;
    private String bgColor;
    private String hoverColor;
    private String hoverBgColor;
    private double weight;

    public ColorCombination(String color, String bgColor, String hoverColor, String hoverBgColor) {
        this.color = color;
        this.bgColor = bgColor;
        this.hoverColor = hoverColor;
        this.hoverBgColor = hoverBgColor;
    }

    public ColorCombination(String color, String bgColor, String hoverColor, String hoverBgColor, double weight) {
        this.color = color;
        this.bgColor = bgColor;
        this.hoverColor = hoverColor;
        this.hoverBgColor = hoverBgColor;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public String getBgColor() {
        return bgColor;
    }

    public String getHoverColor() {
        return hoverColor;
    }

    public String getHoverBgColor() {
        return hoverBgColor;
    }

    public double getWeight() {
        return weight;
    }
}
