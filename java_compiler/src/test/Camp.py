class Node:
    def __init__(self, pointer = None):
        self.children = []
        if parent == None and pointer != None:
            self.pointer = pointer
            # self.parent = pointer.parent
            self.children = pointer.children
        elif pointer == None:
            self.pointer = self
            self.children = []


class Tree:
    def __init__(self):
        self.root = None
        self.cwd = self.root
    def insert(self, sub):
        if self.root == None:
            self.root = sub
            self.cwd = self.root
        else:
            sub.parent = self.cwd
            self.cwd.children.append(sub)



def main():
    t = Tree()
    s1 = Node("1")
    t.insert(s1)
    s2 = Node("2", s1)
    t.insert(s2)
    print(s1.children)

main()