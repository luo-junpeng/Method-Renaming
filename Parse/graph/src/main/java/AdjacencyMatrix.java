import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 有向图邻接矩阵简单实现
 * {@link AdjacencyMatrix#AdjacencyMatrix(E[], E[][])}  构建有向图
 * {@link AdjacencyMatrix#DFS()}  深度优先遍历无向图
 * {@link AdjacencyMatrix#BFS()}  广度优先遍历无向图
 * {@link AdjacencyMatrix#toString()} ()}  输出无向图
 *
 * @author lx
 */
public class AdjacencyMatrix<E> {

    /**
     * 顶点数组
     */
    private Object[] vertexs;
    /**
     * 邻接矩阵
     */
    private int[][] matrix;

    /**
     * 创建有向图
     *
     * @param vexs  顶点数组
     * @param edges 边二维数组
     */
    public AdjacencyMatrix(E[] vexs, E[][] edges) {
        // 初始化顶点数组,并添加顶点
        vertexs = Arrays.copyOf(vexs, vexs.length);
        // 初始化边矩阵,并填充边信息
        matrix = new int[vexs.length][vexs.length];
        for (E[] edge : edges) {
            // 读取一条边的起始顶点和结束顶点索引值 p1,p2表示边方向p1->p2
            int p1 = getPosition(edge[0]);
            int p2 = getPosition(edge[1]);
            //p1 出度的位置 置为1
            matrix[p1][p2] = 1;
            //无向图和有向图的邻接矩阵实现的区别就在于下面这一行代码
            //matrix[p2][p1] = 1;
        }
    }

    /**
     * 获取某条边的某个顶点所在顶点数组的索引位置
     *
     * @param e 顶点的值
     * @return 所在顶点数组的索引位置, 或者-1 - 表示不存在
     */
    private int getPosition(E e) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == e) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 深度优先搜索遍历图,类似于树的前序遍历,
     */
    public void DFS() {
        //新建顶点访问标记数组,对应每个索引对应相同索引的顶点数组中的顶点
        boolean[] visited = new boolean[vertexs.length];
        //初始化所有顶点都没有被访问
        for (int i = 0; i < vertexs.length; i++) {
            visited[i] = false;
        }
        System.out.println("DFS: ");
        for (int i = 0; i < vertexs.length; i++) {
            if (!visited[i]) {
                DFS(i, visited);
            }
        }
        System.out.println();
    }

    /**
     * 深度优先搜索遍历图的递归实现,类似于树的先序遍历
     * 因此模仿树的先序遍历,同样借用栈结构,这里使用的是方法的递归,隐式的借用栈
     *
     * @param i       顶点索引
     * @param visited 访问标志数组
     */
    private void DFS(int i, boolean[] visited) {
        visited[i] = true;
        System.out.print(vertexs[i] + " ");
        // 遍历该顶点的所有邻接点。若该邻接点是没有访问过，那么继续递归遍历领接点
        for (int w = firstVertex(i); w >= 0; w = nextVertex(i, w)) {
            if (!visited[w]) {
                DFS(w, visited);
            }
        }
    }

    /**
     * 返回顶点v的第一个邻接顶点的索引，失败则返回-1
     *
     * @param v 顶点v在数组中的索引
     * @return 返回顶点v的第一个邻接顶点的索引，失败则返回-1
     */
    private int firstVertex(int v) {
        //如果索引超出范围,则返回-1
        if (v < 0 || v > (vertexs.length - 1)) {
            return -1;
        }
        /*根据邻接矩阵的规律:顶点索引v对应着边二维矩阵的matrix[v][i]一行记录
         * 从i=0开始*/
        for (int i = 0; i < vertexs.length; i++) {
            if (matrix[v][i] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1
     *
     * @param v 顶点索引
     * @param w 第一个邻接点索引
     * @return 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1
     */
    private int nextVertex(int v, int w) {
        //如果索引超出范围,则返回-1
        if (v < 0 || v > (vertexs.length - 1) || w < 0 || w > (vertexs.length - 1)) {
            return -1;
        }
        /*根据邻接矩阵的规律:顶点索引v对应着边二维矩阵的matrix[v][i]一行记录
         * 由于邻接点w的索引已经获取了,所以从i=w+1开始寻找*/
        for (int i = w + 1; i < vertexs.length; i++) {
            if (matrix[v][i] == 1) {
                return i;
            }
        }
        return -1;
    }

    /*
     * 广度优先搜索（类似于树的层次遍历）
     */
    public void BFS() {
        // 辅组队列
        Queue<Integer> indexLinkedList = new LinkedList<>();
        //新建顶点访问标记数组,对应每个索引对应相同索引的顶点数组中的顶点
        boolean[] visited = new boolean[vertexs.length];
        for (int i = 0; i < vertexs.length; i++) {
            visited[i] = false;
        }
        System.out.println("BFS: ");
        for (int i = 0; i < vertexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.print(vertexs[i] + " ");
                indexLinkedList.add(i);
            }
            if (!indexLinkedList.isEmpty()) {
                //j索引出队列
                Integer j = indexLinkedList.poll();
                //继续访问j的邻接点
                for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) {
                    if (!visited[k]) {
                        visited[k] = true;
                        System.out.print(vertexs[k] + " ");
                        //继续入队列
                        indexLinkedList.add(k);
                    }
                }
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                stringBuilder.append(matrix[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        Character[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        Character[][] edges = {
                {'A', 'C'},
                //对于无向图来说是多余的边关系,在linkLast方法中做了判断,并不会重复添加
                {'A', 'D'},
                {'A', 'D'},
                {'D', 'A'},

                {'A', 'F'},
                {'B', 'C'},
                {'C', 'D'},
                {'E', 'G'},
                {'E', 'B'},
                {'D', 'B'},
                {'F', 'G'}};
        //构建图
        AdjacencyMatrix<Character> undirectedAdjacencyMatrix = new AdjacencyMatrix<>(vexs, edges);
        //输出图
        System.out.println(undirectedAdjacencyMatrix);
        //深度优先遍历
        undirectedAdjacencyMatrix.DFS();
        //广度优先遍历
        undirectedAdjacencyMatrix.BFS();
    }
}
