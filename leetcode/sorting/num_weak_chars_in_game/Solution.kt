class Solution {
    fun numberOfWeakCharacters(properties: Array<IntArray>): Int {
        val n = properties.size
        val sortedByAttack = Array<Character?>(n) {null}
        val sortedByDefense = Array<Character?>(n) {null}
        for ((i, prop) in properties.withIndex()) {
            val curr = Character(i, prop[0], prop[1])
            sortedByAttack[i] = curr
            sortedByDefense[i] = curr
        }
        sortedByAttack.sortBy { it!!.attack }
        sortedByDefense.sortBy { it!!.defense }

        var i = 0
        var j = n - 1
        var count = 0
        val processed = mutableSetOf<Character>()
        while (i < n) {
            val curr = sortedByAttack[i]!!
            if (sortedByDefense[j] == curr) {
                j--
                i++
                continue
            }
            var k = j
            while (sortedByDefense[k]!! != curr) {
                val combatant = sortedByDefense[k]
                // If combatant has already been processed, or if its attack and defense are equal, then skip.
                // Game rules are that a player is weak only iff its attack or strength are LESS than the combatant's
                if (combatant!! in processed || curr.attack == combatant.attack || curr.defense == combatant.defense) {
                    k--
                } else {
                    count++
                    break
                }
            }
            i++
            processed.add(curr)
        }
        return count
    }

    private data class Character(
        val id: Int,
        val attack: Int,
        val defense: Int
    )
}
