package com.example.fenerbahce1907.model;

public class Match {
    private String utcDate;
    private String status;
    private Score score;
    private Team homeTeam;
    private Team awayTeam;
    private Competition competition;

    public String getUtcDate() {
        return utcDate;
    }

    public Score getScore() {
        return score;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Competition getCompetition() {
        return competition;
    }

    public static class Score {
        public FullTime fullTime;

        public static class FullTime {
            public Integer home;
            public Integer away;
        }
    }

    public static class Team {
        public String name;
        public String crest; // ✅ Logo URL'si (PNG, SVG, vs.)
    }


    public static class Competition {
        public String name;
    }
}
