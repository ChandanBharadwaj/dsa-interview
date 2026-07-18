#!/usr/bin/env python3
"""Regenerates pages/27-problem-map.html from the anchored problem blocks on all
topic pages. Run from the repo root after adding problems:  python3 tools/gen-map.py
Every <details class="sol" id="..."> on pages 01-24 and 26 becomes one table row;
the "why this concept" cue comes from CUES below (add a line per new problem)."""
import re, glob, sys

PAGES = [
    ('01', '01-arrays-hashing.html',      'Arrays & Hashing'),
    ('02', '02-two-pointers.html',        'Two Pointers'),
    ('03', '03-sliding-window.html',      'Sliding Window'),
    ('04', '04-prefix-sum.html',          'Prefix Sum'),
    ('05', '05-matrix.html',              'Matrix & 2D Simulation'),
    ('06', '06-sorting.html',             'Sorting'),
    ('07', '07-binary-search.html',       'Binary Search'),
    ('08', '08-linked-lists.html',        'Linked Lists'),
    ('09', '09-stacks-queues.html',       'Stacks & Queues'),
    ('10', '10-recursion-backtracking.html', 'Recursion & Backtracking'),
    ('11', '11-trees.html',               'Trees & BSTs'),
    ('12', '12-heaps.html',               'Heaps & Priority Queues'),
    ('13', '13-tries.html',               'Tries'),
    ('14', '14-strings-advanced.html',    'String Algorithms'),
    ('15', '15-graphs-basics.html',       'Graph Basics'),
    ('16', '16-graphs-advanced.html',     'Graphs Advanced'),
    ('17', '17-shortest-paths.html',      'Shortest Paths & MST'),
    ('18', '18-dp-1d.html',               'Dynamic Programming 1D'),
    ('19', '19-dp-2d.html',               'Dynamic Programming 2D'),
    ('20', '20-dp-advanced.html',         'DP Advanced'),
    ('21', '21-greedy-intervals.html',    'Greedy & Intervals'),
    ('22', '22-bit-manipulation.html',    'Bit Manipulation'),
    ('23', '23-math-geometry.html',       'Math & Geometry'),
    ('24', '24-advanced-structures.html', 'Advanced Structures & Design'),
    ('26', '26-interview-playbook.html',  'Interview Playbook — the decision-gym drills'),
]

# One line per concept: where this pattern shows up in production (links into page 25).
REAL = {
    '01': 'recommendation co-visitation counting and item similarity (<a href="25-real-world-systems.html#reco">reco systems</a>), hash indexes and dedup (<a href="25-real-world-systems.html#db">databases</a>)',
    '02': 'merge steps inside databases and log compaction (<a href="25-real-world-systems.html#db">databases</a>), diff tools (<a href="25-real-world-systems.html#git">Git</a>)',
    '03': 'trending counters and stream analytics (<a href="25-real-world-systems.html#sched">streaming</a>), rate limiters (<a href="25-real-world-systems.html#infra">infra</a>)',
    '04': 'pre-aggregated metrics and OLAP cubes (<a href="25-real-world-systems.html#db">databases</a>), capacity planning (<a href="25-real-world-systems.html#sched">schedulers</a>)',
    '05': 'image transforms, spreadsheet engines, cellular-automata simulation (<a href="25-real-world-systems.html#sched">simulation</a>)',
    '06': 'external sort in databases and MapReduce shuffles (<a href="25-real-world-systems.html#db">databases</a>)',
    '07': 'index lookups and LSM-tree reads (<a href="25-real-world-systems.html#db">databases</a>), autoscaling thresholds (<a href="25-real-world-systems.html#infra">infra</a>)',
    '08': 'LRU chains in caches (<a href="25-real-world-systems.html#infra">infra</a>), free lists in allocators',
    '09': 'undo stacks, expression parsers and compilers, monotonic price spans in trading (<a href="25-real-world-systems.html#sched">streaming</a>)',
    '10': 'config-space search, dependency resolution back-off, puzzle/route solvers (<a href="25-real-world-systems.html#maps">navigation</a>)',
    '11': 'B-tree indexes and file systems (<a href="25-real-world-systems.html#db">databases</a>), DOM/AST processing (<a href="25-real-world-systems.html#git">Git trees</a>)',
    '12': 'top-K ranking stages (<a href="25-real-world-systems.html#reco">reco systems</a>), task queues (<a href="25-real-world-systems.html#sched">schedulers</a>)',
    '13': 'search typeahead (<a href="25-real-world-systems.html#reco">reco systems</a>), IP routing tables, spell-check (<a href="25-real-world-systems.html#db">search engines</a>)',
    '14': 'plagiarism and dedup fingerprinting, grep/editors, DNA matching (<a href="25-real-world-systems.html#db">search engines</a>)',
    '15': '"users like you" traversal (<a href="25-real-world-systems.html#reco">reco systems</a>), degrees of separation (<a href="25-real-world-systems.html#social">social networks</a>)',
    '16': 'build systems and package managers (topological order), community detection (<a href="25-real-world-systems.html#social">social networks</a>), Git DAG (<a href="25-real-world-systems.html#git">Git</a>)',
    '17': 'turn-by-turn routing (<a href="25-real-world-systems.html#maps">Google Maps</a>), network routing protocols (<a href="25-real-world-systems.html#infra">infra</a>)',
    '18': 'retry/backoff planning, resource allocation, spell-correct scoring (<a href="25-real-world-systems.html#db">search</a>)',
    '19': 'diff algorithms (<a href="25-real-world-systems.html#git">Git</a>), bioinformatics alignment, translation memories',
    '20': 'trading-strategy state machines, matrix-chain query planners (<a href="25-real-world-systems.html#db">databases</a>)',
    '21': 'meeting/room scheduling (<a href="25-real-world-systems.html#sched">schedulers</a>), interval maps in storage engines (<a href="25-real-world-systems.html#db">databases</a>)',
    '22': 'permission masks, bitmap indexes (<a href="25-real-world-systems.html#db">databases</a>), feature flags, bloom-filter internals (<a href="25-real-world-systems.html#infra">infra</a>)',
    '23': 'sharding and consistent hashing arithmetic (<a href="25-real-world-systems.html#infra">infra</a>), weighted load balancing, A/B bucketing',
    '24': 'caches at every layer (<a href="25-real-world-systems.html#infra">infra</a>), rate limiters, time-series and versioned stores (<a href="25-real-world-systems.html#db">databases</a>)',
    '26': 'the interview itself — one problem, many algorithms, and the constraints that flip the verdict',
}

# "Why this concept applies" — one line per problem, keyed "page:id".
CUES = {
    '01:lc217': 'Seen-before test — one HashSet membership check per element.',
    '01:lc242': 'Frequency counting: two strings are anagrams iff their count maps match.',
    '01:lc1':   'Complement lookup: “have I seen target−x?” is one HashMap probe.',
    '01:lc49':  'Canonical key (sorted word / count signature) groups anagrams in one map.',
    '01:lc347': 'Count map + bucket-by-frequency beats sorting for top-K.',
    '01:lc238': 'Prefix × suffix products — the no-division two-pass trick.',
    '01:lc128': 'Set membership lets each streak be walked once from its true start.',
    '01:lc41':  'Index-as-hash: cyclic sort puts value v at index v−1; the first hole answers.',
    '01:lc383': 'Frequency counting as inventory: stock the magazine, spend on the note.',
    '01:lc169': 'HashMap count — or Boyer-Moore voting for O(1) space.',
    '01:lc205': 'Bijection check: two maps, one per direction.',
    '01:lc290': 'The same bijection, letters ↔ whole words.',
    '01:lc202': 'Repeat detection: HashSet of seen values (or Floyd at two speeds).',
    '01:lc219': 'A windowed HashSet: membership over only the last k elements.',
    '01:lc448': 'The array is its own hash table — mark presence by negating.',
    '01:lc271': 'No delimiter is safe → length-prefix framing, like network protocols.',
    '01:lc2013':'Fix the diagonal partner; the two forced corners are O(1) count lookups.',
    '02:lc125': 'Converging pointers compare both ends, skipping non-alphanumerics.',
    '02:lc167': 'Sorted input: move the pair inward based on sum vs target.',
    '02:lc26':  'Reader/writer parallel pointers compact the array in place.',
    '02:lc11':  'Move the shorter wall inward — the classic discard argument.',
    '02:lc15':  'Sort, fix one element, two-pointer the remaining pair; skip duplicates.',
    '02:lc42':  'Water at i = min(maxLeft, maxRight) − height; two pointers track both maxes.',
    '02:lc344': 'The converging-pointer skeleton with nothing else attached.',
    '02:lc27':  'Reader/writer: reader scans all, writer advances over keepers.',
    '02:lc283': 'Reader/writer compaction plus a zero-filled tail.',
    '02:lc977': 'Largest squares live at the ends — converge and fill backwards.',
    '02:lc680': 'Converge; the one deletion must happen at the first mismatch.',
    '02:lc16':  '3Sum’s pointer engine, recording the closest miss instead of exact hits.',
    '02:lc18':  'kSum recursion: fix two, two-pointer the rest, dedup every level.',
    '02:lc287': 'i → nums[i] is a linked list; the duplicate is the cycle entrance (Floyd).',
    '03:lc121': 'Min-so-far scan = a window whose left edge only jumps at a new low.',
    '03:lc643': 'Fixed window: add the entering element, subtract the leaving one.',
    '03:lc3':   'Variable window + last-seen map; jump the left edge past repeats.',
    '03:lc567': 'Fixed-size window comparing 26-letter histograms.',
    '03:lc76':  'Expand to cover, shrink to minimal — the canonical variable window.',
    '03:lc239': 'Monotonic deque keeps the window maximum at the front.',
    '03:lc485': 'The degenerate window: grow on 1s, hard-reset on a 0.',
    '03:lc1456':'Fixed window, one counter — update only what enters and leaves.',
    '03:lc209': 'Shrink while it still qualifies — positivity makes the sum monotonic.',
    '03:lc904': '“At most 2 distinct types” = count-map window that shrinks on a third.',
    '03:lc424': 'Legal while size − maxFreq ≤ k; the stale maxFreq is provably safe.',
    '03:lc1004':'“Flip ≤ k zeros” reframed: window may contain at most k zeros.',
    '03:lc438': 'Histogram window + an agreement counter for O(1) slides.',
    '04:lc1480':'The definition of a prefix sum, verbatim.',
    '04:lc303': 'Precompute once; any range sum becomes two lookups.',
    '04:lc560': 'Prefix + HashMap: count earlier prefixes equal to currentSum − k.',
    '04:lc525': 'Map 0→−1; equal 0/1 counts mean the same prefix value seen twice.',
    '04:lc238': 'Prefix/suffix products — the additive trick with × instead of +.',
    '04:lc1094':'Difference array: +passengers at pickup, − at drop-off, then scan.',
    '04:lc724': 'One running left sum + the total: right side by subtraction.',
    '04:lc304': '2D prefix table; any rectangle = four lookups (inclusion–exclusion).',
    '04:lc974': 'Divisible subarray ⇔ two prefixes share a remainder class.',
    '04:lc523': 'Remember each remainder’s FIRST index; distance ≥ 2 seals it.',
    '04:lc1109':'Range adds become two point edits; integrate once at the end.',
    '04:lc862': 'Negatives kill the window — prefix sums + monotonic deque of starts.',
    '05:lc867': 'Pure index gymnastics: out[c][r] = in[r][c].',
    '05:lc54':  'Four shrinking boundaries walk the spiral layer by layer.',
    '05:lc48':  'Transpose + reverse each row = 90° rotation in place.',
    '05:lc73':  'First row/column double as zero markers — O(1) extra space.',
    '05:lc289': 'Encode the next state in spare bits for an in-place simulation.',
    '05:lc36':  'Row/column/box seen-sets checked in a single sweep.',
    '05:lc566': 'One linear index k ↔ (k/cols, k%cols) bridges both shapes.',
    '05:lc766': 'Whole-diagonal constancy collapses to “equal up-left neighbor”.',
    '05:lc59':  'The spiral boundaries again, writing 1..n² instead of reading.',
    '05:lc240': 'Top-right staircase: each compare discards a full row or column.',
    '05:lc1329':'Diagonals are named by row − col: group by key, sort each group.',
    '06:lc88':  'Merge from the back so nothing gets overwritten.',
    '06:lc1365':'Value range 0–100 → counting sort beats comparisons.',
    '06:lc75':  'Dutch national flag: three-way partition in one pass.',
    '06:lc215': 'Quickselect: partition until the pivot lands at index k.',
    '06:lc179': 'Custom comparator: order by (a+b) vs (b+a) concatenation.',
    '06:lc148': 'Merge sort is the natural O(n log n) for linked lists.',
    '06:lc315': 'Merge sort counts cross-half inversions while it merges.',
    '06:lc905': 'Quicksort’s partition step bare, with “is even” as the pivot test.',
    '06:lc1122':'Counting sort emitting in a custom order: arr2 first, leftovers ascending.',
    '06:lc912': 'Implement the engines: merge, random-pivot 3-way quick, heap.',
    '06:lc274': 'Clamp citations at n — the range becomes bounded, counting sort applies.',
    '06:lc164': 'Pigeonhole: the max sorted gap must span a bucket boundary.',
    '07:lc704': 'The classic halving template.',
    '07:lc278': 'Boundary search: first index where the predicate flips to true.',
    '07:lc35':  'lowerBound: first index with value ≥ target.',
    '07:lc34':  'Two boundary searches: first ≥ target and first &gt; target.',
    '07:lc33':  'One half is always sorted — decide which, then recurse into it.',
    '07:lc153': 'Compare mid with the right end to find the unrotated minimum.',
    '07:lc875': 'Binary search the answer: “can Koko finish at speed s?” is monotonic.',
    '07:lc1011':'Search on capacity; each feasibility check is a greedy scan.',
    '07:lc4':   'Partition both arrays so the left halves hold exactly half the elements.',
    '08:lc206': 'The three-pointer rewire loop every list problem builds on.',
    '08:lc21':  'Dummy head + tail-append merge.',
    '08:lc141': 'Floyd: the fast pointer catches the slow one iff a cycle exists.',
    '08:lc876': 'Fast moves 2, slow moves 1 — slow stops at the middle.',
    '08:lc19':  'Two pointers n apart reach the end and the target together.',
    '08:lc2':   'Schoolbook digit-by-digit addition with a carry.',
    '08:lc143': 'Middle + reverse the second half + interleave.',
    '08:lc138': 'Interleave copies between originals (or an old→new map).',
    '08:lc142': 'After the meeting, reset one pointer to head; they meet at the cycle start.',
    '08:lc25':  'Reverse k nodes at a time; count the group before committing.',
    '09:lc20':  'A stack of expected closing brackets.',
    '09:lc155': 'Each node stores min-so-far alongside its value.',
    '09:lc232': 'Two stacks: transfer reverses order, amortized O(1).',
    '09:lc150': 'Postfix evaluation: push operands, pop two per operator.',
    '09:lc739': 'Monotonic decreasing stack of not-yet-answered days.',
    '09:lc853': 'Sort by position; fleets form where arrival times collide.',
    '09:lc22':  'Backtracking on open/close counts — the call stack is the stack.',
    '09:lc84':  'Monotonic stack finds each bar’s nearer-smaller boundaries.',
    '10:lc78':  'Include/exclude decision per element — the subset tree.',
    '10:lc46':  'Choose each unused element per slot (swap or used[] array).',
    '10:lc17':  'Cartesian product via one recursion depth per digit.',
    '10:lc39':  'Reuse allowed → stay on the same index after choosing it.',
    '10:lc79':  'Grid DFS with mark-and-unmark of the visited cell.',
    '10:lc131': 'Cut at every palindromic prefix, recurse on the remainder.',
    '10:lc51':  'Column/diagonal sets prune before each queen is placed.',
    '11:lc104': 'Depth = 1 + max of children — structural recursion.',
    '11:lc226': 'Swap the children at every node.',
    '11:lc100': 'Compare both trees node-by-node in lockstep.',
    '11:lc543': 'Diameter through a node = left depth + right depth; track a global max.',
    '11:lc102': 'BFS with a per-level size snapshot.',
    '11:lc98':  'Pass down (min,max) bounds — parent-child checks are not enough.',
    '11:lc235': 'BST order: the LCA is where p and q split.',
    '11:lc230': 'In-order traversal visits a BST in sorted order; stop at k.',
    '11:lc297': 'Preorder with null markers round-trips the exact shape.',
    '12:lc703': 'Size-k min-heap: the root IS the kth largest.',
    '12:lc1046':'Max-heap: repeatedly smash the two heaviest stones.',
    '12:lc973': 'Size-k max-heap by distance keeps the k closest points.',
    '12:lc347': 'Count map + size-k min-heap (compare with page 01’s buckets).',
    '12:lc621': 'Most-frequent-first greedy with a cooldown queue.',
    '12:lc23':  'A heap of the k list heads; pop-push until all lists drain.',
    '12:lc295': 'Two heaps split the stream; the median lives at their tops.',
    '13:lc14':  'Vertical scan = walking a single trie path.',
    '13:lc208': 'The node-per-character structure itself.',
    '13:lc648': 'Walk each word; stop at the first root flag you meet.',
    '13:lc211': 'Trie DFS that branches on “.” wildcards.',
    '13:lc677': 'Store running prefix sums on every node during insert.',
    '13:lc1268':'Trie walk carrying the 3 lexicographically-first completions per node.',
    '13:lc212': 'One trie for all words; DFS the grid against it, pruning dead branches.',
    '14:lc125': 'Two pointers from both ends (page 02’s pattern on strings).',
    '14:lc459': 'If s occurs inside (s+s) minus its end characters, s is a repeated unit.',
    '14:lc443': 'Read/write pointers compress character runs in place.',
    '14:lc5':   'Expand around each of the 2n−1 palindrome centers.',
    '14:lc647': 'Same center expansion, counting every palindrome hit.',
    '14:lc28':  'KMP’s failure function skips re-matching known prefixes.',
    '15:lc733': 'Repaint from the seed — the hello-world of graph traversal.',
    '15:lc200': 'Each DFS flood consumes one island; count the launches.',
    '15:lc695': 'A flood fill that returns the size of its region.',
    '15:lc994': 'Multi-source BFS: every rotten cell starts at distance 0.',
    '15:lc542': 'Multi-source BFS from all zeros gives every distance at once.',
    '15:lc133': 'Traverse with an old→new map to clone as you go.',
    '15:lc417': 'Reverse thinking: search inward from both oceans.',
    '15:lc1091':'Unweighted shortest path = plain BFS, eight directions.',
    '16:lc547': 'Union-find (or DFS) over the adjacency matrix counts components.',
    '16:lc207': 'A cycle in the prerequisite digraph = impossible; Kahn’s in-degrees.',
    '16:lc210': 'The topological order IS the answer.',
    '16:lc684': 'The first edge whose endpoints already share a root closes the cycle.',
    '16:lc721': 'Union the emails; the components are the people.',
    '16:lc785': 'Two-color with BFS; any odd cycle breaks bipartiteness.',
    '16:lc269': 'Extract edges from adjacent word pairs, then topo-sort the letters.',
    '17:lc1091':'Unweighted grid → BFS; DP fails because moves go all 8 ways (cycles).',
    '17:lc743': 'Classic Dijkstra with a lazy-deletion heap.',
    '17:lc787': 'Edge-count cap → Bellman-Ford, whose rounds mean “≤ k+1 edges”.',
    '17:lc1584':'Not shortest path — connect everything cheaply: Kruskal’s MST.',
    '17:lc1631':'Minimize the max edge → Dijkstra variant or binary search + BFS.',
    '17:lc778': 'Same minimize-the-max family, on rising water.',
    '18:lc70':  'f(n) = f(n−1) + f(n−2): memo → table → two variables.',
    '18:lc746': 'The same recurrence with step costs; finish from either last step.',
    '18:lc198': 'Rob-or-skip choice: dp[i] = max(dp[i−1], dp[i−2] + v).',
    '18:lc322': 'Unbounded min-coins: try every coin from every amount.',
    '18:lc139': 'dp[i]: can s[0..i) be segmented? Try every word ending at i.',
    '18:lc300': 'LIS: O(n²) table, then patience piles + binary search for O(n log n).',
    '18:lc152': 'Track max AND min products — a negative flips them.',
    '18:lc213': 'The circle constraint = two linear house-robber runs.',
    '19:lc62':  'paths(r,c) = up + left; a combinatorics shortcut exists too.',
    '19:lc64':  'Grid DP works because moves only go right/down — the grid is a DAG.',
    '19:lc1143':'The 2D subsequence table: match → diag+1, else max(up, left).',
    '19:lc72':  'Three edit operations = three table neighbors; LCS’s harder sibling.',
    '19:lc416': 'Subset-sum knapsack: can some subset hit total/2?',
    '19:lc518': 'Count combinations: coins outer, amounts inner — loop order matters.',
    '19:lc97':  'dp[i][j]: can prefixes of s1 and s2 interleave into s3’s prefix?',
    '19:lc516': 'LPS = LCS(s, reverse(s)), or interval DP straight on the string.',
    '20:lc122': 'Hold/cash two-state machine.',
    '20:lc309': 'Add a cooldown state — a three-state machine.',
    '20:lc337': 'Tree DP: each node returns (robbed, skipped).',
    '20:lc312': 'Interval DP: choose the LAST balloon popped in each range.',
    '20:lc877': 'Game DP on the score difference; the current player maximizes.',
    '20:lc698': 'Bitmask over chosen elements, pruned bucket by bucket.',
    '20:lc188': 'k transactions → a 2k-state machine over (day, txn, holding).',
    '21:lc55':  'Track the furthest reach; one scan decides.',
    '21:lc763': 'Each letter’s last occurrence bounds its partition.',
    '21:lc56':  'Sort by start; overlapping intervals absorb into one.',
    '21:lc435': 'Sort by end; keep the earliest-ending compatible interval.',
    '21:lc253': 'Peak overlap count = rooms; min-heap of end times or a sweep line.',
    '21:lc134': 'Failure at i eliminates every start ≤ i; the net total decides feasibility.',
    '21:lc57':  'Sorted disjoint input → before/absorb/after in one pass.',
    '21:lc452': 'Interval scheduling inverted: shoot at the earliest end.',
    '22:lc136': 'XOR cancels pairs; the survivor remains.',
    '22:lc338': 'bits(i) = bits(i&gt;&gt;1) + (i&amp;1) — DP on the bits themselves.',
    '22:lc268': 'XOR indices against values; the missing one survives.',
    '22:lc191': 'n &amp; (n−1) clears the lowest set bit — count the iterations.',
    '22:lc190': 'Peel bits off one end, push them onto the other.',
    '22:lc371': 'XOR = sum without carry, AND≪1 = carry; loop until the carry dies.',
    '22:lc137': 'Count bits mod 3 (or the two-register state machine).',
    '23:lc204': 'The sieve: mark multiples starting from each prime squared.',
    '23:lc50':  'Square-and-multiply halves the exponent every step.',
    '23:lc384': 'Fisher-Yates: swap each i with a uniform j ≤ i.',
    '23:lc1071':'The s1+s2 == s2+s1 test, then Euclid on the lengths.',
    '23:lc171': 'Bijective base-26 parsing: result = result·26 + digit.',
    '23:lc528': 'Prefix sums turn weights into ranges; binary-search the dart.',
    '23:lc189': 'Three reversals rotate in place; normalize with k %= n first.',
    '24:lc146': 'HashMap + doubly linked list = O(1) get/put in recency order.',
    '24:lc380': 'Array + index map; swap-with-last makes remove O(1).',
    '24:lc307': 'Fenwick tree: point update and prefix query, both O(log n).',
    '24:lc729': 'TreeMap neighbors: only the floor and ceiling bookings can conflict.',
    '24:lc362': 'Timestamp queue vs 300 circular buckets — the window-counting trade.',
    '24:lc981': 'Floor binary search over an append-only (hence sorted) version list.',
    '24:lc460': 'Frequency buckets of LinkedHashSets plus a minFreq pointer.',
    '26:p-drill-1-find-a-pair-summing-to-target-3-algorithms': 'Brute force vs HashMap vs sort + two pointers — memory and sortedness flip the verdict.',
    '26:p-drill-2-top-k-elements-4-algorithms': 'Sort vs heap vs quickselect vs buckets — n, k, and streaming decide.',
    '26:p-drill-3-count-the-connected-groups-3-algorithms': 'DFS vs BFS vs union-find — static counting vs dynamic merging.',
    '26:p-drill-4-cheapest-path-through-a-grid-4-algorithms': 'Grid DP vs BFS vs Dijkstra vs minimize-max — the movement rules decide (the screenshot problem).',
    '26:p-drill-5-how-many-meeting-rooms-4-algorithms': 'Heap vs sweep line vs difference array vs TreeMap — same counting, four costumes.',
    '26:p-drill-6-sum-of-a-range-many-times-4-structures': 'Recompute vs prefix vs Fenwick vs segment tree — the update/query mix decides.',
    '26:p-drill-7-best-contiguous-stretch-window-vs-prefix-m': 'Window vs prefix-map vs Kadane — negatives and the objective decide.',
}

HEAD = '''<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>27 · Concept → Problem Map — DSA Interview Runbook</title>
<link rel="stylesheet" href="../assets/style.css">
<script src="../assets/app.js"></script>
</head>
<body>

<header class="site">
  <div class="wrap">
    <a class="brand" href="../index.html">⟵ DSA Runbook</a>
    <nav>
      <a href="../index.html#curriculum">All topics</a>
      <a href="../index.html#cheatsheet">Cheat-sheet</a>
    </nav>
  </div>
</header>

<div class="wrap">

<h1>The Concept → Problem Map</h1>
<p class="subtitle">Every fully worked problem on this site, indexed by the concept that solves it — with a one-line cue for <em>why</em> that concept applies, and where the pattern runs in production.</p>
<p>
  <span class="badge phase">Reference · use throughout</span>
  <span class="badge freq">The reverse index of the whole runbook</span>
</p>

<div class="why">
  <strong>How to use this page.</strong> Reading a topic teaches you a pattern; this page answers the
  reverse question — <em>“show me everything this pattern solves.”</em> Each row links straight to the
  fully worked solution (statement, examples, brute→optimal variations in commented Java). Cover the
  cue column and quiz yourself: read the problem name, say the cue out loud, then click through and
  check. The <strong>Real-world</strong> line under each table points at the production systems on
  <a href="25-real-world-systems.html">page 25</a> that run the same pattern. Remember the grid
  shortest-path question that started this runbook? It sits in
  <a href="17-shortest-paths.html">17 · Shortest Paths</a> and gets the full four-algorithm face-off in
  <a href="26-interview-playbook.html#p-drill-4-cheapest-path-through-a-grid-4-algorithms">drill 4 of the playbook</a>.
</div>
'''

TAIL = '''
<div class="note">
  <strong>This page grows with the runbook.</strong> Every newly worked problem lands here
  automatically, so if a table looks short today it will fill out as more problems are added to its
  topic page. The drills of the <a href="26-interview-playbook.html">Interview Playbook</a> are listed
  last — they are the “one problem, many algorithms” capstone this whole map builds toward.
</div>

<nav class="pager">
  <a class="prev" href="26-interview-playbook.html"><span class="dir">⟵ Prev</span><span class="name">26 · Interview Playbook</span></a>
  <a class="next" href="../index.html#cheatsheet"><span class="dir">Next ⟶</span><span class="name">Master cheat-sheet</span></a>
</nav>

</div>
</body>
</html>
'''

def main():
    rows_by_page, missing = {}, []
    for num, fname, _t in PAGES:
        src = open('pages/' + fname).read()
        rows = []
        for m in re.finditer(
                r'<details class="sol" id="([^"]+)">\s*<summary><span class="badge ([a-z0-9 ]+)">[^<]*</span>\s*(.*?)</summary>',
                src, re.S):
            aid, diff, raw = m.group(1), m.group(2).split()[0], m.group(3)
            title = re.sub(r'\s+', ' ', re.sub(r'<[^>]+>', '', raw)).strip()
            lc = re.search(r'LeetCode (\d+)', title)
            disp = re.sub(r'\s*—\s*LeetCode \d+.*$', '', title)
            cue = CUES.get(f'{num}:{aid}')
            if cue is None:
                cue = ''
                missing.append(f'{num}:{aid}')
            rows.append((aid, diff, disp, lc.group(1) if lc else '—', cue))
        rows_by_page[num] = rows

    out = [HEAD]
    toc = ' ·\n  '.join(f'<a href="#m-{n}">{n} {t.split(" — ")[0]}</a>' for n, _f, t in PAGES)
    out.append(f'<p class="muted small" style="line-height:2">Jump to:\n  {toc}\n</p>\n')
    for num, fname, title in PAGES:
        out.append(f'\n<h2 id="m-{num}">{num} · {title}</h2>\n<div class="tablewrap">\n<table>')
        out.append('\n  <tr><th>Problem</th><th>LC</th><th>Difficulty</th><th>Why this concept</th></tr>')
        for aid, diff, disp, lc, cue in rows_by_page[num]:
            badge = 'drill' if diff not in ('easy', 'medium', 'hard') else diff
            dclass = diff if diff in ('easy', 'medium', 'hard') else 'medium'
            out.append(f'\n  <tr><td><a href="{fname}#{aid}">{disp}</a></td>'
                       f'<td>{lc}</td>'
                       f'<td><span class="badge {dclass}">{badge if badge != "drill" else "drill"}</span></td>'
                       f'<td>{cue}</td></tr>')
        out.append('\n</table>\n</div>')
        out.append(f'\n<p class="small"><strong>Real-world:</strong> {REAL[num]}.</p>\n')
    out.append(TAIL)
    open('pages/27-problem-map.html', 'w').write(''.join(out))
    n = sum(len(r) for r in rows_by_page.values())
    print(f'wrote pages/27-problem-map.html: {n} problems across {len(PAGES)} concepts')
    if missing:
        print('MISSING CUES (add to tools/gen-map.py):')
        for k in missing:
            print(' ', k)

if __name__ == '__main__':
    main()
