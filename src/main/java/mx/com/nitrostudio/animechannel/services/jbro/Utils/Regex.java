package mx.com.nitrostudio.animechannel.services.jbro.Utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    private String data;
    private Pattern pattern;
    private Matcher matcher;

    public Regex(String pattern, String data)
    {
        this.pattern = Pattern.compile(pattern);
        this.data = data;
        this.matcher = this.pattern.matcher(this.data);
    }

    public Regex(Pattern pattern, String data)
    {
        this.pattern = pattern;
        this.data = data;
        this.matcher = pattern.matcher(this.data);
    }

    public Matcher getMatcher()
    {
        return matcher;
    }

    public String[][] getMatches() {
        if (this.matcher == null) {
            return null;
        } else {
            final Matcher matcher = this.matcher;
            matcher.reset();
            final ArrayList<String[]> ar = new ArrayList<String[]>();
            while (matcher.find()) {
                final int c = matcher.groupCount();
                int d = 1;
                String[] group;
                if (c == 0) {
                    group = new String[c + 1];
                    d = 0;
                } else {
                    group = new String[c];
                }
                for (int i = d; i <= c; i++) {
                    final String tmp = matcher.group(i);
                    group[i - d] = tmp;
                }
                ar.add(group);
            }
            return ar.size() == 0 ? new String[][] {} : ar.toArray(new String[][] {});
        }
    }

    public String[] getRow(final int y) {
        if (this.matcher != null) {
            final Matcher matcher = this.matcher;
            matcher.reset();
            int groupCount = 0;
            while (matcher.find()) {
                if (groupCount == y) {
                    final int c = matcher.groupCount();

                    final String[] group = new String[c];

                    for (int i = 1; i <= c; i++) {
                        final String tmp = matcher.group(i);
                        group[i - 1] = tmp;
                    }
                    return group;
                }
                groupCount++;
            }
        }
        return null;
    }

    public String getMatch(int entry, final int group) {
        if (this.matcher != null) {
            final Matcher matcher = this.matcher;
            matcher.reset();
            // group++;
            entry++;
            int groupCount = 0;
            while (matcher.find()) {
                if (groupCount == group) {
                    final String ret = matcher.group(entry);
                    return ret;
                }
                groupCount++;
            }
        }
        return null;
    }

    public String getMatch(final int group) {
        if (this.matcher != null) {
            final Matcher matcher = this.matcher;
            matcher.reset();
            if (matcher.find()) {
                final String ret = matcher.group(group + 1);
                return ret;
            }
        }
        return null;
    }

    public String[] getColumn(int x) {
        if (this.matcher == null) {
            return null;
        } else {
            x++;
            final Matcher matcher = this.matcher;
            matcher.reset();

            final java.util.List<String> ar = new ArrayList<String>();
            while (matcher.find()) {
                final String tmp = matcher.group(x);
                ar.add(tmp);
            }
            return ar.toArray(new String[ar.size()]);
        }
    }

    public int count() {
        if (this.matcher == null) {
            return 0;
        } else {
            this.matcher.reset();
            int c = 0;
            final Matcher matchertmp = this.matcher;
            while (matchertmp.find()) {
                c++;
            }
            return c;
        }
    }

    public boolean matches() {
        final Matcher matcher = this.matcher;
        if (matcher == null) {
            return false;
        } else {
            matcher.reset();
            return matcher.find();
        }
    }

}
