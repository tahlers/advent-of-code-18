package adventofcode18

import kotlin.math.min

object Day7 {

    enum class Status {FINISHED, WORKING}

    data class Worker(val status: Status, val remainingSeconds: Int, val worksOn: Char?) {
        fun startWork(seconds: Int, c: Char): Worker {
            val newStatus = if (seconds <= 0) Status.FINISHED else Status.WORKING
            return if (status==Status.FINISHED) Worker(newStatus, seconds, c) else throw IllegalStateException()
        }
        fun work(): Worker {
            return if (this.remainingSeconds - 1 <= 0) Worker(Status.FINISHED, 0, this.worksOn)
            else this.copy(remainingSeconds = remainingSeconds - 1)
        }
    }

    fun stepOrder(input: String): String {
        val lines = input.lines()
        val steps = lines.map { Pair(it[5], it[36]) }
        val allSingleSteps = steps.flatMap { it.toList() }.distinct()
        val dependencyMap = steps.groupBy { it.second }.mapValues { it.value.map { c -> c.first } }
        return ordering("", allSingleSteps, dependencyMap)
    }

    tailrec fun ordering(result: String,
                         candidates: List<Char>,
                         predecessorMap: Map<Char, List<Char>>): String {
        val candidatesNotAlreadyInResult = candidates.filter { it !in result }
        val candidatesWithPredecessorFulfilled = candidatesNotAlreadyInResult
                .filter { predecessorMap.getOrDefault(it, emptyList()).all { c -> c in result } }
        val sorted = candidatesWithPredecessorFulfilled.sorted()

        return if (sorted.isEmpty()) result else ordering(result + sorted.first(), candidates, predecessorMap)
    }

    fun letterSeconds(c: Char, offset: Int): Int = c.toInt() - 64 + offset

    fun secondsSpend(input: String, workerCount: Int, offset: Int): Int {
        val lines = input.lines()
        val steps = lines.map { Pair(it[5], it[36]) }
        val allSingleSteps = steps.flatMap { it.toList() }.distinct()
        val dependencyMap = steps.groupBy { it.second }.mapValues { it.value.map { c -> c.first } }
        val workers = (1..workerCount).map { Worker(Status.FINISHED, 0, null) }
        return simulate(0, workers, "", allSingleSteps, dependencyMap, offset)
    }

    fun simulate(second: Int,
                 worker: List<Worker>,
                 result: String,
                 candidates: List<Char>,
                 predecessorMap: Map<Char, List<Char>>,
                 offset: Int): Int {
        val currentlyWorkedOn = worker.filter { it.status == Status.WORKING}.map { it.worksOn }
        val finishedWork = worker.filter { it.status == Status.FINISHED }.map { it.worksOn }.filterNotNull()
        val newResult = result + finishedWork.joinToString("")
        val candidatesNotAlreadyInResult = candidates.filter { it !in (newResult + currentlyWorkedOn) }
        val candidatesWithPredecessorFulfilled = candidatesNotAlreadyInResult
                .filter { predecessorMap.getOrDefault(it, emptyList()).all { c -> c in newResult} }
        val sorted = candidatesWithPredecessorFulfilled.sorted()
        return if (sorted.isEmpty() && currentlyWorkedOn.isEmpty()) second else {



            val newWorkerList = worker.sortedBy { it.status.ordinal }.mapIndexed { index, w ->
                val nextItem = sorted.getOrNull(index)
                if (w.status == Status.FINISHED && nextItem != null) {
                    w.startWork(letterSeconds(nextItem, offset)-1, nextItem)
                } else if (w.status == Status.FINISHED) {
                    w.copy(worksOn = null)
                } else {
                    w.work()
                }
            }
            simulate(second+1, newWorkerList,newResult, candidates, predecessorMap, offset )
        }
    }


}