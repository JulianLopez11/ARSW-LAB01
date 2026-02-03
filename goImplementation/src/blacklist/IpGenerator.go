package blacklist

import (
	"fmt"
	"math/rand"
)

func GenerateIPs(rng *rand.Rand, n int) []string {
	ips := make([]string, 0, n)

	for i := 0; i < n; i++ {
		ip := fmt.Sprintf("%d.%d.%d.%d",
			rng.Intn(256),
			rng.Intn(256),
			rng.Intn(256),
			rng.Intn(256),
		)
		ips = append(ips, ip)
	}
	return ips
}
