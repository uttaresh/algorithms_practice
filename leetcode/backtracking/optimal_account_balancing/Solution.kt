// https://leetcode.com/problems/optimal-account-balancing/submissions/
class Solution {
    private var minTxnsToSettle = Int.MAX_VALUE
    
    fun minTransfers(transactions: Array<IntArray>): Int {
        val balances = calculateBalances(transactions)      
        val credits = ArrayList(balances.values.filter { it > 0 })
        val debits = ArrayList(balances.values.filter { it < 0 })
        settleBalances(0, credits, debits)
        return minTxnsToSettle
    }
    
    private fun settleBalances(txnsSoFar: Int, credits: ArrayList<Int>, debits: ArrayList<Int>) {
        // if the path we're on is already more than a min path we found earlier, skip this whole path:
        if (txnsSoFar >= minTxnsToSettle) return
        
        // if the balance is zero, record this as the new min path to settle
        if (credits.size == 0 && debits.size == 0) {
            minTxnsToSettle = txnsSoFar
            return
        }
        
        var minTxns = Int.MAX_VALUE
        // each debt must be paid in full. our only choice is which credit to pay
        // in this call, let's focus on paying the first debt (or a portion of it)
        val debit = debits[0] 
        for (j in 0 until credits.size) {
            val credit = credits[j]

            // try paying as much as we can of the owed amount from this (debit) borrower to this (credit) lender
            val txnAmt = minOf(credit, -1 * debit)
            credits[j] = credits[j] - txnAmt
            debits[0] = debits[0] + txnAmt
            var debitRemoved = false
            if (debits[0] == 0) {
                debits.removeAt(0)
                debitRemoved = true
            }
            var creditRemoved = false
            if (credits[j] == 0) {
                credits.removeAt(j)
                creditRemoved = true
            }

            // settle what's left
            settleBalances(txnsSoFar + 1, credits, debits)  
            
            // backtrack:
            if (debitRemoved) debits.add(0, 0)
            if (creditRemoved) credits.add(j, 0)
            debits[0] = debits[0] - txnAmt
            credits[j] = credits[j] + txnAmt            
        }
    }
    
    private fun calculateBalances(transactions: Array<IntArray>): HashMap<Int, Int> {
        val balances = HashMap<Int, Int>() // k: person id, v: credit/(-debit)
        for (t in transactions) {
            val amt = t[2]
            
            // credit the person who lent money:
            val lender = t[0]
            if (lender !in balances) balances[lender] = 0
            balances[lender] = balances[lender]!! + amt
            
            // debit the person who borrowed money:
            val borrower = t[1]
            if (borrower !in balances) balances[borrower] = 0
            balances[borrower] = balances[borrower]!! - amt
        }
        return balances
    }
}
