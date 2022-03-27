/*
 *	// Definition for an Interval.
 *	class Interval {
 *		var start:Int = 0
 *		var end:Int = 0
 *	
 *		constructor(_start:Int, _end:Int) {
 *			start = _start
 *			end = _end
 *		}
 *	}
 */

class Solution {
    fun employeeFreeTime(schedule: ArrayList<ArrayList<Interval>>): ArrayList<Interval> {        
        val shifts = ArrayList<Interval>()
        for (employeeSchedule in schedule) {
            for (shift in employeeSchedule) {
                shifts.add(shift)
            }
        }
        shifts.sortBy { it.start }
        var curr = 0
        var next = 1
        while (next < shifts.size) {
            if (shifts[curr].end >= shifts[next].start) {
                // if this shift ends after next one begins, they are overlapping. merge
                shifts[curr].end = maxOf(shifts[curr].end, shifts[next].end)
                shifts.removeAt(next)
            } else {
                curr = next
                next++
            }            
        }        
        val unstaffedPeriods = ArrayList<Interval>()
        for (i in 0..shifts.size-2) {
            unstaffedPeriods.add(Interval(shifts[i].end, shifts[i+1].start))
        }                
        return unstaffedPeriods        
    }
}
