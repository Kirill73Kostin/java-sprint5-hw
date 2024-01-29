package taskmanager.manager;

import taskmanager.model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private int MAX_COUNT = 10;
    public static final ArrayList<Task> history = new ArrayList<>();
    private  static  class Node {
        Node prev;
        Node next;
        Task task;

        public Node(Node prev, Node next, Task task) {
            this.prev = prev;
            this.next = next;
            this.task = task;
        }
    }

    Node first;
    Node last;
    public Map<Integer, Node> nodes = new HashMap<>();
    @Override
    public List<Task> getHistory() {
        Node current = first;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        //return List.copyOf(history);
        return history;
    }

    @Override
    public void addTaskToHistory(Task task) {
        //remove(task.getId());
        Node node = new Node(last, null, task);
        if(last != null) {
            last.next = node;
        }
        if(first == null) {
            first = node;
        } else {
        last.next = node;
        }
        last = node;
        nodes.put(task.getId(), node);
    }

    @Override
    public void remove(int id) {
        Node remove = nodes.remove(id);
        if (remove == null) {
            return;
        }
        if (remove.prev == null) {
            first = remove.next;
        } else {
            remove.prev.next = remove.next;
        }
        if (remove.next == null) {
            last = remove.prev;
        } else {
            remove.next.prev = remove.prev;
        }
    }

}
