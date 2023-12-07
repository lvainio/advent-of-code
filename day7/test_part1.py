import unittest
import part1

class TestPart1(unittest.TestCase):

    def test_get_type(self):
        self.assertEquals(part1.get_type("AAAAA"), 7)
        self.assertEquals(part1.get_type("AAQAA"), 6)
        self.assertEquals(part1.get_type("AQAQA"), 5)
        self.assertEquals(part1.get_type("AQAJA"), 4)
        self.assertEquals(part1.get_type("AQAQ1"), 3)
        self.assertEquals(part1.get_type("AQJTA"), 2)
        self.assertEquals(part1.get_type("23456"), 1)

    def test_compare_hands(self):
        self.assertEquals(part1.compare_hands(("AAAAA", 0), ("AQ234", 0)), 1)
        self.assertEquals(part1.compare_hands(("AAAAA", 0), ("AAAAA", 0)), 0)
        self.assertEquals(part1.compare_hands(("AAAAJ", 0), ("AAAAQ", 0)), -1)

if __name__ == '__main__':
    unittest.main()