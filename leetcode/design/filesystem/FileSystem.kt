class FileSystem() {
    private val root = DirNode("")

    fun ls(path: String): List<String> {
        val node = getNode(path)
        if (node is DirNode) {
            val childrenNames = mutableListOf<String>()
            for (childName in node.children.keys) {
                childrenNames.add(childName)
            }
            childrenNames.sort()
            return childrenNames
        } else return listOf(node.name)
    }

    fun mkdir(path: String) {
        val pathList = path.split("/")
        var currNode:TreeNode = root
        for (nodeName in pathList) {
            if (nodeName == "") continue
            if (currNode is DirNode) {
                if (nodeName !in currNode.children) {
                    currNode.children[nodeName] = DirNode(nodeName)
                }
                currNode = currNode.children[nodeName]!!
            } else throw IllegalArgumentException("Cannot create dir. A file ${currNode.name} exists in path $path")
        }
    }

    fun addContentToFile(filePath: String, content: String) {
        val pathList = filePath.split("/")
        var currNode:TreeNode = root
        for ((i, nodeName) in pathList.withIndex()) {
            if (nodeName == "") continue
            if (currNode is DirNode) {
                if (nodeName !in currNode.children) {
                    if (i == pathList.size - 1) {
                        currNode.children[nodeName] = FileNode(nodeName, content)
                    } else {
                        currNode.children[nodeName] = DirNode(nodeName)
                    }
                } else if (i == pathList.size - 1) {
                    val file = currNode.children[nodeName]!! as FileNode
                    file.contents = file.contents + content
                } else {
                    currNode = currNode.children[nodeName]!!
                }
            } else throw IllegalArgumentException(
                "Cannot create dir. A file ${currNode.name} exists in the middle of path $filePath")
        }
    }

    fun readContentFromFile(filePath: String): String {
        val node = getNode(filePath) as FileNode
        return node.contents
    }

    private fun getNode(path: String): TreeNode {
        val pathList = path.split("/")
        var currNode:TreeNode = root
        for (nodeName in pathList) {
            if (nodeName == "") continue
            if (currNode is DirNode) {
                currNode = currNode.children[nodeName]!!
            } else throw IllegalArgumentException("Not a directory: ${currNode.name}")

        }
        return currNode
    }

    interface TreeNode {
        val name: String
    }

    data class DirNode(
        override val name: String,
        val children: MutableMap<String, TreeNode> = mutableMapOf()
    ): TreeNode

    data class FileNode(
        override val name: String,
        var contents: String
    ): TreeNode
}
