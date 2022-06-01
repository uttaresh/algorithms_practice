// Submission: https://leetcode.com/submissions/detail/711873361/
class Solution {
    private lateinit var memo: IntArray

    fun jobScheduling(startTime: IntArray, endTime: IntArray, profit: IntArray): Int {
        val n = startTime.size
        val jobs = ArrayList<Job>(n)
        for (i in startTime.indices) {
            jobs.add(Job(startTime[i], endTime[i], profit[i]))
        }
        jobs.sortWith( compareBy { it.startTime } )
        memo = IntArray(n) { -1 }
        return findMaxProfit(jobs, 0)
    }

    private fun findMaxProfit(jobs: ArrayList<Job>, start: Int): Int {
        if (start > jobs.size - 1) {
            return 0
        }
        fun execute(): Int {
            val currJob = jobs[start]
            // consider case that we didn't take this job:
            val profitWithout = findMaxProfit(jobs, start + 1)

            // consider the case that we did take this job and skipped conflicting jobs:
            var firstNonConflicting = jobs.size
            for (i in start + 1 until jobs.size) {
                if (jobs[i].startTime >= currJob.endTime) {
                    firstNonConflicting = i
                    break
                }
            }

            val profitWith = currJob.profit + findMaxProfit(jobs, firstNonConflicting)
            return maxOf(profitWith, profitWithout)
        }
        if (memo[start] == -1) {
            memo[start] = execute()
        }
        return memo[start]
    }

    data class Job(
        val startTime: Int,
        val endTime: Int,
        val profit: Int
    )
}
