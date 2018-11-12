package model;

import org.apache.log4j.Logger;

public class MapParameters {

    private static final Logger logger = Logger.getLogger(MapParameters.class);

    public Boolean amenity = true;
    public Boolean barrier = true;
    public Boolean borderType = true;
    public Boolean building = true;
    public Boolean coastline = true;
    public Boolean cuisine = true;
    public Boolean compacted = true;
    public Boolean earth = true;
    public Boolean footway = true;
    public Boolean forest = true;
    public Boolean grass = true;
    public Boolean ground = true;
    public Boolean highway = true;
    public Boolean historic = true;
    public Boolean leisure = true;
    public Boolean name = true;
    public Boolean natural = true;
    public Boolean neighbourhood = true;
    public Boolean tourism = true;
    public Boolean parking = true;
    public Boolean path = true;
    public Boolean place = true;
    public Boolean recycling = true;
    public Boolean residential = true;
    public Boolean sand = true;
    public Boolean sport = true;
    public Boolean wall = true;
    public Boolean water = true;
    public Boolean waterway = true;
    public Boolean wood = true;

    public Boolean canBeDrawn(String type) {
        if ("amenity".equalsIgnoreCase(type)) {
            return amenity;
        } else if ("barrier".equalsIgnoreCase(type)) {
            return barrier;
        } else if ("borderType".equalsIgnoreCase(type)) {
            return borderType;
        } else if ("building".equalsIgnoreCase(type)) {
            return building;
        } else if ("coastline".equalsIgnoreCase(type)) {
            return coastline;
        } else if ("cuisine".equalsIgnoreCase(type)) {
            return cuisine;
        } else if ("compacted".equalsIgnoreCase(type)) {
            return compacted;
        } else if ("earth".equalsIgnoreCase(type)) {
            return earth;
        } else if ("footway".equalsIgnoreCase(type)) {
            return footway;
        } else if ("forest".equalsIgnoreCase(type)) {
            return forest;
        } else if ("grass".equalsIgnoreCase(type)) {
            return grass;
        } else if ("ground".equalsIgnoreCase(type)) {
            return ground;
        } else if ("highway".equalsIgnoreCase(type)) {
            return highway;
        } else if ("historic".equalsIgnoreCase(type)) {
            return historic;
        } else if ("leisure".equalsIgnoreCase(type)) {
            return leisure;
        } else if ("name".equalsIgnoreCase(type)) {
            return name;
        } else if ("natural".equalsIgnoreCase(type)) {
            return natural;
        } else if ("neighbourhood".equalsIgnoreCase(type)) {
            return neighbourhood;
        } else if ("tourism".equalsIgnoreCase(type)) {
            return tourism;
        } else if ("parking".equalsIgnoreCase(type)) {
            return parking;
        } else if ("path".equalsIgnoreCase(type)) {
            return path;
        } else if ("place".equalsIgnoreCase(type)) {
            return place;
        } else if ("recycling".equalsIgnoreCase(type)) {
            return recycling;
        } else if ("residential".equalsIgnoreCase(type)) {
            return residential;
        } else if ("sand".equalsIgnoreCase(type)) {
            return sand;
        } else if ("sport".equalsIgnoreCase(type)) {
            return sport;
        } else if ("wall".equalsIgnoreCase(type)) {
            return wall;
        } else if ("water".equalsIgnoreCase(type)) {
            return water;
        } else if ("waterway".equalsIgnoreCase(type)) {
            return waterway;
        } else if ("wood".equalsIgnoreCase(type)) {
            return wood;
        } else {
            logger.info(type + "cannot be drawn");
            return false;
        }
    }
}
