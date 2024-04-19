import java.util.TreeSet;

public class Solution {
    // TODO: Include your data structures here
    private Team[] allTeams;
    private TreeSet<Team> betterTeams = new TreeSet<>();


    public Solution(int numTeams) {
        // TODO: Construct/Initialise your data structures here
        allTeams = new Team[numTeams];
        for (int i = 0; i < numTeams; i++) {
            allTeams[i] = new Team();
        }
    }

    public int update(int team, long newPenalty){
        // TODO: Implement your update function here
        Team team1 = allTeams[0];
        if (team == 1) {
            team1.update(newPenalty);
            while (betterTeams.size() != 0 && team1.compareTo(betterTeams.last()) <= 0) {
                betterTeams.pollLast();
            }
        } else {
            Team otherTeam = allTeams[team - 1];
            otherTeam.update(newPenalty);
            if (otherTeam.compareTo(team1) < 0) {
                betterTeams.add(otherTeam);
            }
        }
        return betterTeams.size() + 1;
    }

    private class Team implements Comparable<Team> {
        private long solved;
        private long penalty;

        private void update(long newPenalty) {
            solved++;
            penalty = newPenalty;
        }

        @Override
        public int compareTo(Team team) {
            int compare = Long.compare(team.solved, solved);
            return compare == 0 ? Long.compare(penalty, team.penalty) : compare;
        }
    }
}
