package adventofcode18

object Day8 {

    data class Node(val children: List<Node>, val metadata: List<Int>)

    fun metadataSum(input: String): Int {
        val tokens = input.split(" ").map { it.toInt() }
        val root = parseNodes(tokens)
        return sumMetaNode(root.first)
    }

    private fun parseNodes(tokens: List<Int>): Pair<Node, List<Int>> {
        val childCount = tokens[0]
        val metaCount = tokens[1]
        val childrenWithRest: Pair<List<Node>, List<Int>> =
                (0 until childCount).fold(Pair(emptyList(), tokens.drop(2))) { acc, _ ->
                    val childWithTokens = parseNodes(acc.second)
                    Pair(acc.first + childWithTokens.first, childWithTokens.second)
                }
        val meta = childrenWithRest.second.take(metaCount)
        return Pair(Node(childrenWithRest.first, meta), childrenWithRest.second.drop(metaCount))
    }


    private fun sumMetaNode(node: Node): Int {
        return node.metadata.sum() + node.children.map { sumMetaNode(it) }.sum()
    }


    fun value(input: String): Int {
        val tokens = input.split(" ").map { it.toInt() }
        val root = parseNodes(tokens)
        return valueNode(root.first)
    }

    private fun valueNode(node: Node): Int {
        return if (node.children.isEmpty()) node.metadata.sum()
        else {
            node.metadata.map { index ->
                val selectedNode = node.children.getOrNull(index - 1)
                if (selectedNode == null) 0 else valueNode(selectedNode)
            }.sum()
        }
    }

}