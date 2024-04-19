import java.util.TreeSet;

public class Solution {
    // TODO: Include your data structures here
    private Team[] teams;
    private TreeSet<Team> set = new TreeSet<>();


    public Solution(int numTeams) {
        // TODO: Construct/Initialise your data structures here
        teams = new Team[numTeams];
        for (int i = 0; i < numTeams; i++) {
            teams[i] = new Team();
            set.add(teams[i]);
        }
    }

    public int update(int team, long newPenalty){
        // TODO: Implement your update function here
        Team currTeam = teams[team - 1];
        currTeam.update(newPenalty);
        set.remove(currTeam);
        set.add(currTeam);
        return set.headSet(teams[0], true).size();
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
