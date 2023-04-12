package com.status.videomaker.Command;

public class Text {
    private String textFitler;

    public Text(int i, int i2, float f, Color color, String str, String str2, Time time) {
        StringBuilder sb = new StringBuilder();
        sb.append("drawtext=fontfile=");
        sb.append(str);
        sb.append(":fontsize=");
        sb.append(f);
        sb.append(":fontcolor=");
        sb.append(color.getColor());
        sb.append(":x=");
        sb.append(i);
        sb.append(":y=");
        sb.append(i2);
        sb.append(":text='");
        sb.append(str2);
        sb.append("'");
        sb.append(time == null ? "" : time.getTime());
        this.textFitler = sb.toString();
    }

    public String getTextFitler() {
        return this.textFitler;
    }

    public static class Time {
        private String time;

        public Time(int i, int i2) {
            this.time = ":enable=between(t\\," + i + "\\," + i2 + ")";
        }

        public String getTime() {
            return this.time;
        }
    }

    public enum Color {
        Red("Red"),
        Blue("Blue"),
        Yellow("Yellow"),
        Black("Black"),
        DarkBlue("DarkBlue"),
        Green("Green"),
        SkyBlue("SkyBlue"),
        Orange("Orange"),
        White("White"),
        Cyan("Cyan");
        
        private String color;

        private Color(String str) {
            this.color = str;
        }

        public String getColor() {
            return this.color;
        }
    }
}
