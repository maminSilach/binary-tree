package org.example.binarytree;


import java.util.ArrayDeque;
import java.util.Deque;

public class BinaryTree {
    private Node root;

    public static final class Node {

        public Node(int key) {
            this.key = key;
        }

        private int key;

        private Node left;

        private Node right;

        public int getKey() {
            return key;
        }
    }


    public void addNode(int value) {
        // Если корня не существует, то вставляемый элемент и будет нашим корнем.
        if (root == null) {
            root = new Node(value);
        } else {
            // Иначе определяем рекурсивный алгоритм вставки.
            addNodeRecursive(root, value);
        }
    }

    public void deleteNode(int value) {
        if (root != null) {
            deleteNodeRecursive(root, value);
        }
    }

    public Node getNode(int value) {

        // Ищем значение только если корень != null
        if (root != null) {
           return getNodeRecursive(root, value);
        }

        throw new RuntimeException("Root is empty");
    }

    public void dfs() {
        Deque<Node> deque = new ArrayDeque<>();

        // Используем методы для стека.
        deque.push(root);

        while (!deque.isEmpty()){

            // Читаем верхнее значение.
            var temp = deque.poll();
            System.out.println("Node with value = " + temp.key);

            // Если правый потомок не пустой добавляем его в deque.
            if (temp.right != null) {
                deque.push(temp.right);
            }

            // Если левый потомок не пустой добавляем его в deque.
            if (temp.left != null) {
                deque.push(temp.left);
            }
        }
    }

    public void dfsInOrder() {
        Deque<Node> deque = new ArrayDeque<>();
        Node current = root;

        while (!deque.isEmpty() || current != null) {

            // Сначала максимально спускаемся в глубину до самого левого элемента.
            if (current != null) {
                deque.push(current);
                current = current.left;
            } else {
                // Здесь current == null. Мы вытаскиваем из стека последний элемент и обрабатываем его.
                current = deque.pop();
                System.out.println("Node with value = " + current.key);

                /* Говорим что current это теперь правое значение считанного узла. Если current == null, то правого элемента у него
                *  нет, это значит что мы будем обрабатывать дальше элементы которые находятся в стеке.
                *  Если current != null, то в стек добавится правый элемент, но ключевое, что он не будет обрабатываться сразу.
                */
                current = current.right;
            }
        }
    }

    public void dfsPostOrder() {
        Deque<Node> deque = new ArrayDeque<>();
        Node current = root;
        Node previous = null; // Отслеживает предыдущий посещенный узел

        while (!deque.isEmpty() || current != null) {

            // Сначала максимально спускаемся в глубину до самого левого элемента.
            if (current != null) {
                deque.push(current);
                current = current.left;
            } else {
                // Если у текущего узла нет правого потомка или
                // если правый потомок уже был посещен
                if (deque.peek().right == null || deque.peek().right == previous) {
                    current = deque.pop();
                    System.out.println("Node with value = " + current.key);
                    previous = current; // Обновляем предыдущий узел
                    current = null; // Переходим к следующему узлу в стеке
                } else {
                    current = deque.peek().right; // Переходим к правому потомку
                }
            }
        }
    }


    public void bfs() {
        Deque<Node> deque = new ArrayDeque<>();

        // Используем методы для очереди.
        deque.add(root);

        while (!deque.isEmpty()){

            // Читаем первое значение.
            var temp = deque.remove();
            System.out.println("Node with value = " + temp.key);

            // Если правый потомок не пустой добавляем его в deque.
            if (temp.right != null) {
                deque.add(temp.right);
            }

            // Если левый потомок не пустой добавляем его в deque.
            if (temp.left != null) {
                deque.add(temp.left);
            }
        }
    }




    public Node getNodeRecursive(Node node, int value){
        // Если дошли максимально в глубину, значит такого значения не существует в нашем дереве.
        if (node == null) {
            throw new RuntimeException("Node with value = %s not found".formatted(value));
        }

        // Если искомое значение меньше значения текущего узла, идем влево.
        if (node.key > value) {
            return getNodeRecursive(node.left, value);
        }

        // Если искомое значение больше значения текущего узла, идем вправо.
        if (node.key < value) {
            return getNodeRecursive(node.right, value);
        }

        return node;
    }



    private Node addNodeRecursive(Node node, int value) {
        // Условия выхода из рекурсии. Если мы дошли до конца дерева (node == null), то нам нужно вставить значение.
        if (node == null) {
            return new Node(value);
        }

        // Если вставляемое значение меньше значения текущего узла, идем влево.
        if (node.key > value) {
            node.left = addNodeRecursive(node.left, value);
        }

        // Если вставляемое значение больше значения текущего узла, идем вправо.
        if (node.key < value) {
            node.right = addNodeRecursive(node.right, value);
        }

        return node;
    }

    private Node deleteNodeRecursive(Node node, int value) {

        // Элемент для удаления не найден
        if (node == null) {
           throw new RuntimeException("Node with value = %s not found".formatted(value));
        }

        // Если удаляемое значение меньше значения текущего узла, идем влево.
         if (node.key > value) {
            node.left = deleteNodeRecursive(node.left, value);
        }

         // Если уделяемое значение больше значения текущего узла, идем вправо.
        if (node.key < value) {
            node.right = deleteNodeRecursive(node.right, value);
        }

        // Значение найдено. Определяем алгоритм самого удаления.
        if (node.key == value) {

            // У узла нет детей. Тогда можем смело просто его удалять.
            if (node.left == null && node.right == null) {
                return null;
            }

             // У узла есть левый потомок. Тогда родителю удаляемого узла присваиваем на место старого значения, левый узел.
            else if (node.left != null && node.right == null) {
                return node.left;
            }

             // У узла есть правый потомок. Тогда родителю удаляемого узла присваиваем на место старого значения, правый узел.
            else if (node.right != null && node.left == null) {
                return node.right;

            } else {
                // У узла есть оба потомка. Определяем наименьший узел правого поддерева.
                var temp = findMinNode(node.right);
                // Устанавливаем это значение на место удаляемого узла.
                node.key = temp.key;
                // Рекурсивно удаляем минимальное значение правого поддерева, т.к это значение теперь будет установлено в другом месте.
                node.right = deleteNodeRecursive(node.right, temp.key);
            }
        }

        return node;
    }

    // Вспомогательная функция, чтобы найти минимальный элемент указанного узла. Здесь мы обходимся циклом.
    private static Node findMinNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }
}
