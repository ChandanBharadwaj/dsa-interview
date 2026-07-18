# DSA Interview Runbook

A complete, self-contained data-structures & algorithms interview prep site for an
experienced Java engineer starting from zero DSA background.

**Live site → https://chandanbharadwaj.github.io/dsa-interview/**

**Or locally → open [`index.html`](index.html) in any browser.** No build step, no
server, no internet needed — everything (styles, scripts, SVG animations) is local.

## What's inside

- **26 topic pages** (`pages/`) across 10 phases, ordered so each topic builds on the
  previous: foundations → arrays & core patterns → sorting/binary search → lists,
  stacks & queues → recursion, trees, heaps, tries → strings → graphs & shortest
  paths → dynamic programming & greedy → bits, math & design → a real-world-systems
  capstone (recommendation systems, Google Maps, Git, databases, and more).
- **A 14-week study roadmap** with week-by-week goals and a progress tracker
  (checkboxes persist in your browser via localStorage).
- **Interactive SVG animations** (Play / Step / Reset) for the mechanics that benefit
  from motion: BFS vs DFS, Dijkstra's frontier, merge/quicksort, the LCS table,
  the sieve of Eratosthenes, and many more.
- **Every topic page** follows the same structure: why the topic exists →
  core concepts → reusable Java pattern templates → when-to-use-what comparison
  tables → real-world applications → curated LeetCode problems (easy → hard) with
  fully-commented worked solutions → pitfalls & interview tips.
- **Runnable Java code** (`code/`): every worked solution is mirrored as a
  standalone, compilable class with an asserting `main`. Verify all of them with:

  ```bash
  for d in code/*/; do
    out="/tmp/jc/$(basename "$d")" && mkdir -p "$out" && javac -d "$out" "$d"*.java &&
    for c in $(ls "$out" | grep -v '\$' | sed 's/\.class//'); do
      java -ea -cp "$out" "$c" 2>/dev/null | grep OK
    done
  done
  ```

Built with plain HTML/CSS/JS (light & dark theme via `prefers-color-scheme`).
