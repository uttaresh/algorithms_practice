package problem_solving.stable_marriages

import java.util.*

// https://www.geeksforgeeks.org/stable-marriage-problem/
fun findStableMarriages(menPrefs: Array<IntArray>, womenPrefs: Array<IntArray>): List<Pair<Int, Int>> {
    val menQ: Queue<Int> = LinkedList()
    for (i in menPrefs.indices) {
        menQ.add(i)
    }
    // engagement where index is woman # and value is man #
    val engagements = IntArray(womenPrefs.size) {-1}

    while (menQ.isNotEmpty()) {
        val thisMan = menQ.poll()
        for (woman in menPrefs[thisMan]) {
            // try your luck with each woman you like, in order of preference
            if (engagements[woman] == -1) {
                // woman is not taken, get engaged
                engagements[woman] = thisMan
                break
            } else {
                // woman is engaged, see if she prefers this man instead
                val otherMan = engagements[woman]
                var breakOffEngagement = false
                for (man in womenPrefs[woman]) {
                    if (man == otherMan) {
                        break
                    } else if (man == thisMan) {
                        breakOffEngagement = true
                        break
                    }
                }
                if (breakOffEngagement) {
                    menQ.add(engagements[woman])
                    engagements[woman] = thisMan
                    break
                }
            }
        }
    }

    val ret = LinkedList<Pair<Int, Int>>()
    for ((w, m) in engagements.withIndex()) {
        ret.add(w to m)
    }
    return ret
}

fun main() {
    val menPrefs = arrayOf(
        intArrayOf(3, 1, 2, 0),
        intArrayOf(1, 0, 2, 3),
        intArrayOf(0, 1, 2, 3),
        intArrayOf(0, 1, 2, 3)
    )

    val womenPrefs = arrayOf(
        intArrayOf(0, 1, 2, 3),
        intArrayOf(0, 1, 2, 3),
        intArrayOf(0, 1, 2, 3),
        intArrayOf(0, 1, 2, 3)
    )
    val resp = findStableMarriages(menPrefs, womenPrefs)
    println(resp)
}
