namespace AOC
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] lines = File.ReadAllLines("input.txt");
            int earliestTime = int.Parse(lines[0]);
            string[] buses = lines[1].Split(',');

            int p1 = Part1(buses, earliestTime);
            long p2 = Part2(buses);

            Console.WriteLine(p1);
            Console.WriteLine(p2);
        }

        static int Part1(string[] buses, int earliestTime) {
            int min = int.MaxValue;
            int busNr = 0;
            foreach (var bus in buses)
            {
                if (!string.Equals(bus, "x"))
                {   
                    int busNumber = int.Parse(bus);
                    if (busNumber - (earliestTime % busNumber) < min) {
                        min = busNumber - (earliestTime % busNumber);
                        busNr = busNumber;
                    }
                }
            }
            return min*busNr;
        }

        static long Part2(string[] buses) {
            List<long> cycles = [];
            List<long> offsets = [];
            for (long i = 0; i < buses.Length; i++) 
            {
                if (!string.Equals(buses[i], "x"))
                {   
                    cycles.Add(long.Parse(buses[i]));
                    offsets.Add(i);
                }
            }
            long N = 1;
            foreach (long cycle in cycles)
            {
                N *= cycle;
            }

            long total = 0;
            for (int i = 0; i < cycles.Count; i++)
            {
                long Ni = N / cycles[i];
                long xi = ModularMultiplicativeInverse(Ni, cycles[i]);
                total += -offsets[i] * Ni * xi;
            }

            return (total % N + N) % N; 
        }

        static long ModularMultiplicativeInverse(long a, long mod)
        {
            long b = a % mod;
            for (int x = 1; x < mod; x++)
            {
                if (b * x % mod == 1)
                {
                    return x;
                }
            }
            return 1;
        }
    }
}