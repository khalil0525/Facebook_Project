package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    LinkedList<FacebookUser> findPathBiBFS(HashMap<Integer, FacebookUser> people, int source, int destination) {
        BFSData sourceData = new BFSData(people.get(source));
        BFSData destData = new BFSData(people.get(destination));

        while (!sourceData.isFinished() && !destData.isFinished()) {

            /* Search out from source. */
            FacebookUser collision = searchDataLevel(people, sourceData, destData);
            if (collision != null)
                return mergePaths(sourceData, destData, collision.getID());

            /* Search out from destination. */
            collision = searchDataLevel(people, destData, sourceData);
            if (collision != null)
                return mergePaths(sourceData, destData, collision.getID());
        }

        return null;
    }


    /* Search one level and return collision, if any. */
    FacebookUser searchDataLevel(HashMap<Integer, FacebookUser> people, BFSData primary, BFSData secondary) {
        int count = primary.toVisit.size();
        for (int i = 0; i < count; i++) {

            PathNode pathNode = primary.toVisit.poll();
            int personld = pathNode.getPerson().getID();

            if (secondary.visited.containsKey(personld))
                return pathNode.getPerson();

            FacebookUser User = pathNode.getPerson();
            ArrayList<Integer> friends = User.getFriends();

            for (int friendid : friends) {
                if (!primary.visited.containsKey(friendid)) {
                    FacebookUser friend = people.get(friendid);
                    PathNode next = new PathNode(friend, pathNode);
                    primary.visited.put(friendid, next);
                    primary.toVisit.add(next);
                }
            }

        }
        return null;
    }

    /* Merge paths where searches met at the connection. */
    LinkedList<FacebookUser> mergePaths(BFSData bfsl, BFSData bfs2, int connection) {
        PathNode endl = bfsl.visited.get(connection);
        PathNode end2 = bfs2.visited.get(connection);

        LinkedList<FacebookUser> pathOne = endl.collapse(false);
        LinkedList<FacebookUser> pathTwo = end2.collapse(true);

        pathTwo.removeFirst(); // remove connection
        pathOne.addAll(pathTwo); // add second path

        return pathOne;
    }


    //*****************************************************************************************************BFSDATA CLASS****************************************************************************************
    class BFSData {
        public Queue<PathNode> toVisit = new LinkedList<PathNode>();
        public HashMap<Integer, PathNode> visited = new HashMap<Integer, PathNode>();

        public BFSData(FacebookUser root) {
            PathNode sourcePath = new PathNode(root, null);
            toVisit.add(sourcePath);
            visited.put(root.getID(), sourcePath);
        }

        public boolean isFinished() {
            return toVisit.isEmpty();
        }
    }

    //*****************************************************************************************************PATHNODE CLASS****************************************************************************************
    class PathNode {
        private FacebookUser User = null;
        private PathNode previousNode = null;

        public FacebookUser getPerson() {
            return User;
        }

        public PathNode(FacebookUser p, PathNode previous) {
            User = p;
            previousNode = previous;
        }


        public LinkedList<FacebookUser> collapse(boolean startsWithRoot) {
            LinkedList<FacebookUser> path = new LinkedList<FacebookUser>();
            PathNode node = this;
            while (node != null) {
                if (startsWithRoot)
                    path.addLast(node.User);
                else
                    path.addFirst(node.User);
                node = node.previousNode;
            }

            return path;
        }
    }

}
