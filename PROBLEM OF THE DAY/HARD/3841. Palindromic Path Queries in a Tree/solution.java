import java.util.*;

class Solution {
    int[] head, to, next;
    int[][] up;
    int[] tin, tout, depth;
    int timer = 0;
    int LOG;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                         String s, String[] queries) {
        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        head = new int[n];
        Arrays.fill(head, -1);

        to = new int[2 * edges.length];
        next = new int[2 * edges.length];

        int idx = 0;
        for (int[] e : edges) {
            to[idx] = e[1];
            next[idx] = head[e[0]];
            head[e[0]] = idx++;

            to[idx] = e[0];
            next[idx] = head[e[1]];
            head[e[1]] = idx++;
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[LOG][n];

        dfs(0, 0);

        BIT bit = new BIT(n + 1);
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (chars[i] - 'a');
            bit.update(tin[i], mask);
            bit.update(tout[i] + 1, mask);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                if (chars[u] == c) continue;

                int change = (1 << (chars[u] - 'a'))
                           ^ (1 << (c - 'a'));

                bit.update(tin[u], change);
                bit.update(tout[u] + 1, change);

                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int mask = bit.query(tin[u])
                         ^ bit.query(tin[v])
                         ^ (1 << (chars[ancestor] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    void dfs(int u, int parent) {
        tin[u] = timer++;
        up[0][u] = parent;

        for (int k = 1; k < LOG; k++) {
            up[k][u] = up[k - 1][up[k - 1][u]];
        }

        for (int i = head[u]; i != -1; i = next[i]) {
            int v = to[i];

            if (v == parent) continue;

            depth[v] = depth[u] + 1;
            dfs(v, u);
        }

        tout[u] = timer - 1;
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) return u;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }

    static class BIT {
        int[] tree;
        int n;

        BIT(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int val) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= val;
            }
        }

        int query(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}