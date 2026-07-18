/* ============================================================
   DSA Interview Runbook — shared runtime
   1. Stepper: step-through engine for inline SVG animations
   2. Java syntax highlighter for <pre class="code"><code> blocks
   3. Progress tracker (localStorage) for index topic checkboxes
   No external dependencies.
   ============================================================ */

/* ---------- 1. Stepper animation engine ----------
   Usage (per animation, in a page-level <script>):

     <div class="anim" id="myAnim">
       <svg viewBox="...">...</svg>
       <div class="anim-caption"></div>
       <div class="anim-controls"></div>
     </div>

     new Stepper('myAnim', {
       totalSteps: 6,
       captions: ['step 0 text', 'step 1 text', ...],   // length totalSteps
       apply: function (svg, step) {
         // Idempotent: set the FULL visual state for `step` (0-based).
         // Helpers: Stepper.setClass(svg, '#id', 'active', bool)
         //          Stepper.movePtr(svg, '#ptr', x, y)
       },
       intervalMs: 1400   // optional, default 1400
     });
*/
function Stepper(rootId, opts) {
  var root = document.getElementById(rootId);
  if (!root) return;
  var svg = root.querySelector('svg');
  var captionEl = root.querySelector('.anim-caption');
  var controls = root.querySelector('.anim-controls');
  var total = opts.totalSteps;
  var captions = opts.captions || [];
  var interval = opts.intervalMs || 1400;
  var step = 0;
  var timer = null;

  controls.innerHTML =
    '<button type="button" class="primary" data-a="play">▶ Play</button>' +
    '<button type="button" data-a="prev">◀ Step back</button>' +
    '<button type="button" data-a="next">Step ▶</button>' +
    '<button type="button" data-a="reset">↺ Reset</button>' +
    '<span class="step-ind"></span>';

  var playBtn = controls.querySelector('[data-a="play"]');
  var indEl = controls.querySelector('.step-ind');

  function render() {
    opts.apply(svg, step);
    if (captionEl) captionEl.textContent = captions[step] || '';
    indEl.textContent = (step + 1) + ' / ' + total;
  }
  function stop() {
    if (timer) { clearInterval(timer); timer = null; }
    playBtn.textContent = '▶ Play';
  }
  function next(fromTimer) {
    if (step < total - 1) { step++; render(); }
    else if (fromTimer) stop();
  }
  controls.addEventListener('click', function (e) {
    var a = e.target.getAttribute && e.target.getAttribute('data-a');
    if (!a) return;
    if (a === 'next') { stop(); next(false); }
    if (a === 'prev') { stop(); if (step > 0) { step--; render(); } }
    if (a === 'reset') { stop(); step = 0; render(); }
    if (a === 'play') {
      if (timer) { stop(); return; }
      if (step >= total - 1) step = 0;
      render();
      playBtn.textContent = '⏸ Pause';
      timer = setInterval(function () { next(true); }, interval);
    }
  });
  render();
}

/* Helpers for apply() callbacks */
Stepper.setClass = function (svg, selector, cls, on) {
  svg.querySelectorAll(selector).forEach(function (el) {
    el.classList.toggle(cls, !!on);
  });
};
Stepper.movePtr = function (svg, selector, x, y) {
  svg.querySelectorAll(selector).forEach(function (el) {
    el.style.transform = 'translate(' + x + 'px,' + (y || 0) + 'px)';
  });
};
Stepper.setText = function (svg, selector, text) {
  var el = svg.querySelector(selector);
  if (el) el.textContent = text;
};

/* ---------- 2. Minimal Java syntax highlighter ----------
   Runs on every <pre class="code"><code> block. Works on plain
   text content (write code with &lt; &gt; &amp; escaped in HTML). */
(function () {
  var KW = '\\b(?:abstract|assert|break|case|catch|class|const|continue|default|do|else|enum|extends|final|finally|for|goto|if|implements|import|instanceof|interface|native|new|package|private|protected|public|return|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|volatile|while|var|record|yield|null|true|false)\\b';
  var TY = '\\b(?:void|int|long|double|float|boolean|char|byte|short|String|Integer|Long|Double|Boolean|Character|Object|List|ArrayList|LinkedList|Map|HashMap|TreeMap|LinkedHashMap|Set|HashSet|TreeSet|LinkedHashSet|Deque|ArrayDeque|Queue|PriorityQueue|Stack|Arrays|Collections|Math|StringBuilder|Comparator|Iterator|Optional|Pair|Random|Character)\\b';

  function esc(s) {
    return s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
  }

  function highlight(src) {
    var out = '';
    var i = 0;
    var n = src.length;
    var plain = '';
    function flushPlain() {
      if (!plain) return;
      var h = esc(plain)
        .replace(new RegExp(KW, 'g'), '<span class="kw">$&</span>')
        .replace(new RegExp(TY, 'g'), '<span class="ty">$&</span>')
        .replace(/\b\d[\d_]*(?:\.\d+)?[LlFfDd]?\b/g, '<span class="nu">$&</span>');
      out += h;
      plain = '';
    }
    while (i < n) {
      var c = src[i];
      var two = src.substr(i, 2);
      if (two === '//') {
        flushPlain();
        var e = src.indexOf('\n', i);
        if (e === -1) e = n;
        out += '<span class="cm">' + esc(src.slice(i, e)) + '</span>';
        i = e;
      } else if (two === '/*') {
        flushPlain();
        var e2 = src.indexOf('*/', i + 2);
        e2 = e2 === -1 ? n : e2 + 2;
        out += '<span class="cm">' + esc(src.slice(i, e2)) + '</span>';
        i = e2;
      } else if (c === '"') {
        flushPlain();
        var j = i + 1;
        while (j < n && src[j] !== '"') { if (src[j] === '\\') j++; j++; }
        j = Math.min(j + 1, n);
        out += '<span class="st">' + esc(src.slice(i, j)) + '</span>';
        i = j;
      } else if (c === "'") {
        flushPlain();
        var k = i + 1;
        while (k < n && src[k] !== "'") { if (src[k] === '\\') k++; k++; }
        k = Math.min(k + 1, n);
        out += '<span class="st">' + esc(src.slice(i, k)) + '</span>';
        i = k;
      } else {
        plain += c;
        i++;
      }
    }
    flushPlain();
    return out;
  }

  document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('pre.code code').forEach(function (block) {
      block.innerHTML = highlight(block.textContent);
    });
  });
})();

/* ---------- 3. Progress tracker (index page) ----------
   Topic cards: <div class="topic-card" data-topic="01-arrays-hashing">
                  <input type="checkbox"> ... </div>
   Progress bar: <div class="progress-bar-inner" id="progressInner"></div>
                 <div class="progress-label" id="progressLabel"></div>  */
(function () {
  var LS_KEY = 'dsa-runbook-progress';

  function load() {
    try { return JSON.parse(localStorage.getItem(LS_KEY)) || {}; }
    catch (e) { return {}; }
  }
  function save(state) {
    try { localStorage.setItem(LS_KEY, JSON.stringify(state)); } catch (e) {}
  }

  document.addEventListener('DOMContentLoaded', function () {
    var cards = document.querySelectorAll('.topic-card[data-topic]');
    if (!cards.length) return;
    var state = load();

    function refreshBar() {
      var doneCount = 0;
      cards.forEach(function (c) { if (state[c.getAttribute('data-topic')]) doneCount++; });
      var pct = Math.round((doneCount / cards.length) * 100);
      var inner = document.getElementById('progressInner');
      var label = document.getElementById('progressLabel');
      if (inner) inner.style.width = pct + '%';
      if (label) label.textContent = doneCount + ' of ' + cards.length + ' topics complete (' + pct + '%)';
    }

    cards.forEach(function (card) {
      var key = card.getAttribute('data-topic');
      var cb = card.querySelector('input[type="checkbox"]');
      if (!cb) return;
      cb.checked = !!state[key];
      card.classList.toggle('done', cb.checked);
      cb.addEventListener('change', function () {
        state[key] = cb.checked;
        save(state);
        card.classList.toggle('done', cb.checked);
        refreshBar();
      });
    });
    refreshBar();
  });
})();
