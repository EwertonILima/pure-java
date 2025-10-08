# 🚦 Multithreading in Java — Step-by-Step Learning

This folder contains small, focused Java examples organized by learning stages to help understand **multithreading** and **concurrent programming** using plain Java.

Each stage includes both **theoretical explanations** and **practical code examples**, following a progressive learning path.

---

## 📌 Stage 1 — Fundamentals (Concepts + Hands-on)

- What is a thread?
- Why use multiple threads?
- Common problems: race conditions, deadlocks
- Creating threads with:
  - `Thread` (base class)
  - `Runnable` (functional interface)

---

## 📌 Stage 2 — Synchronization & Shared Data

- `synchronized` keyword
- `volatile` keyword
- `wait`, `notify`, `notifyAll`
- Issues with shared data and thread visibility

---

## 📌 Stage 3 — Modern Concurrency APIs (`java.util.concurrent`)

- `ExecutorService`
- `Callable`, `Future`
- `ScheduledExecutorService`
- `ThreadPoolExecutor`
- Advanced utilities:
  - `ReentrantLock`
  - `Semaphore`
  - `CountDownLatch`
  - `CyclicBarrier`

---

## 📌 Stage 4 — Best Practices, Debugging & Profiling

- When should you use multithreading?
- Common design mistakes
- Thread profiling and performance analysis
- Tools for debugging thread issues

---

## 📌 Stage 5 — Real-World Use Cases

- Parallelizing API calls
- Simulating a lightweight concurrent server
- Scheduling delayed tasks (e.g., queue, cache expiration)
- Safe concurrency patterns (e.g., Producer/Consumer)

---

💡 **Note**: This project uses only standard Java (no Spring or third-party frameworks). Each class is isolated and easy to run for testing and learning.

📁 Each stage is represented by subfolders and organized to make the learning curve clear and hands-on.