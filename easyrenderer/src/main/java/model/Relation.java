package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Relation model class
 */
public class Relation implements Element {

    private Double id;
    private List<Double> ways;
    private List<Tag> tags;
    private List<Member> members;

    public Double getId() {
        return id;
    }

    public List<Double> getWayIds() {
        return ways;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setId(Double d) {
        id = d;
    }

    public void setWays(List<Double> w) {
        ways = w;
    }

    public void addWay(Double w) {
        if (ways == null) {
            ways = new ArrayList<>();
        }
        ways.add(w);
    }

    public void setTags(List<Tag> t) {
        tags = t;
    }

    public void setMembers(List<Member> m) {
        members = m;
    }

    public void addTag(Tag t) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(t);
    }

    public Relation(Double myId, List<Double> waysId, List<Tag> tags, List<Member> members) {
        setId(myId);
        setWays(waysId);
        setTags(tags);
        setMembers(members);
    }

    public Relation() {
    }

    public void addMember(Member member) {
        if (members == null) {
            members = new ArrayList<>();
        }
        members.add(member);
    }
}
