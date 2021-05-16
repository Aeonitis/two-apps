package main

/**
Pre-requisite: go mod download github.com/blang/semver/v4
*/
import (
	"log"
	"os"
	"github.com/blang/semver/v4"
	"strings"
)

/**
Compare two version numbers version1 and version2.

You may assume that the version strings are non-empty and contain only digits and the 'dot'
character. The 'dot' character does not represent a decimal point and is used to separate
number sequences. For instance '2.5' is not "two and a half" or "half way to version three", it is
the fifth second-level revision of the second first-level revision.
Here is an example of version numbers ordering: 0.1 < 1.1 < 1.2 < 1.2.9.9.9.9 < 1.3 < 1.3.4 < 1.10

*/

func main() {

	// Take first two arguments
	versionA := os.Args[1]
	versionB := os.Args[2]

	compareVersion(versionA, versionB)
	//compareSemVer(versionA, versionB)
}

/**
* Compare my way
* Usage: go run VersionCompare.go 1.2 1.2.9.9.9.9
 */
func compareVersion(versionA, versionB string) int {

	// Split strings into string slice/list
	DotDelimiter := "."

	versionASlice := strings.Split(versionA, DotDelimiter)
	lengthOfVersionA := len(versionASlice)
	log.Println("versionA:",versionASlice, "Length:", lengthOfVersionA)

	versionBSlice := strings.Split(versionB, DotDelimiter)
	lengthOfVersionB := len(versionBSlice)
	log.Println("versionB:",versionBSlice, "Length:", lengthOfVersionB)

	// Equalize version elements for fair comparisons
	if lengthOfVersionA != lengthOfVersionB {
		log.Println("Unequal element length in both slices. Equalizing lengths...")

		// Add zeroes on the slice with smaller length either way
		if lengthOfVersionA > lengthOfVersionB {
			log.Println("Smaller slice versionB nominated for extension:", versionBSlice)
			versionBSlice = appendDelimitedZeroesSuffixToSlice(versionBSlice, lengthOfVersionA-lengthOfVersionB)
		} else {
			log.Println("Smaller slice versionA nominated for extension:", versionASlice)
			versionASlice = appendDelimitedZeroesSuffixToSlice(versionASlice, lengthOfVersionB-lengthOfVersionA)
		}

	}

	// Should return compared result on the equalized element size versions

	return 0
}

/**
appendDelimitedZeroesSuffixToSlice takes the smaller slice and add zeroes to equalize them
*/
func appendDelimitedZeroesSuffixToSlice(sliceToAppend []string, zeroesToAdd int) []string {

	Zero := "0"
	var suffixOfZeroes []string

	// Create a slice of zeroes
	for i := 0; i < zeroesToAdd; i++ {
		suffixOfZeroes = append(suffixOfZeroes, Zero)
	}
	
	log.Println("Appending", sliceToAppend, "with suffixOfZeroes:", suffixOfZeroes)
	sliceToAppend = append(sliceToAppend, suffixOfZeroes...)
	log.Println("Result after appending:", sliceToAppend)
	return sliceToAppend
}

/**
* Via SemVer library For semantic versions, e.g. 1.0.0-beta with Major.Minor.Patch
* Usage: go run VersionCompare.go 1.2.0 1.0.0-beta
 */
func compareSemVer(versionX, versionY string) {

	log.Println("VersionX: " + versionX)
	log.Println("VersionY: " + versionY)

	validV1, err := semver.Make(versionX)
	if err != nil {
		log.Println("Error parsing version:", err)
		return
	}

	validV2, err := semver.Make(versionY)
	if err != nil {
		log.Println("Error parsing version:", err)
		return
	}

	log.Println("Result of version comaprison: ", validV1.Compare(validV2))
	log.Println("Is v1 less than v2", validV1.LT(validV2))
	log.Println("Is v1 greater than v2", validV1.GT(validV2))
}
