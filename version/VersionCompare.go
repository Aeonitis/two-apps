package main

/**
Pre-requisite: go mod download github.com/blang/semver/v4
*/
import (
	"fmt"
	"log"
	"os"
	"github.com/blang/semver/v4"
	"strconv"
	"strings"
)

/**
Results to return
If version1 > version2 return 1
If version1 < version2 return -1
otherwise return 0
*/
const (
	ResultVersionAIsMoreThanVersionB = 1
	ResultVersionAIsLessThanVersionB = -1
	ResultVersionAIsEqualToVersionB  = 0
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

	// Take first two console arguments
	versionA := os.Args[1]
	versionB := os.Args[2]

	comparisonResult := compareVersion(versionA, versionB)

	fmt.Println("RESULT --------------")
	fmt.Println(comparisonResult)

	//compareSemVer(versionA, versionB)
}

/**
* compareVersion Compare my way, split, then equalize both versions by comparing per element
* Usage: go run VersionCompare.go 1.2 1.2.9.9.9.9
 */
func compareVersion(versionA, versionB string) int {

	// Split strings into string slice/list
	DotDelimiter := "."

	versionASlice := strings.Split(versionA, DotDelimiter)
	lengthOfVersionA := len(versionASlice)
	log.Println("versionA:", versionASlice, "Length:", lengthOfVersionA)

	versionBSlice := strings.Split(versionB, DotDelimiter)
	lengthOfVersionB := len(versionBSlice)
	log.Println("versionB:", versionBSlice, "Length:", lengthOfVersionB)

	// Equalize version elements for fair comparisons
	if lengthOfVersionA != lengthOfVersionB {
		log.Println("Unequal element length in both slices. Equalizing lengths...")

		// Add zeroes on the slice with smaller length either way
		if lengthOfVersionA > lengthOfVersionB {
			log.Println("Smaller length slice versionB nominated for extension:", versionBSlice)
			versionBSlice = appendDelimitedZeroesSuffixToSlice(versionBSlice, lengthOfVersionA-lengthOfVersionB)
		} else {
			log.Println("Smaller length slice versionA nominated for extension:", versionASlice)
			versionASlice = appendDelimitedZeroesSuffixToSlice(versionASlice, lengthOfVersionB-lengthOfVersionA)
		}

	}

	// Should return compared result on the equalized element size versions
	return compareEachElementInSlices(versionASlice, versionBSlice)
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
compareEachElementInSlices Assuming both lengths are equal, compare each version iterating from left to right
*/
func compareEachElementInSlices(sliceA, sliceB []string) int {
	log.Println("Comparison of equal length slices:", sliceA, sliceB)

	for index := range sliceA {
		// Compare each element as a digit e.g. 300 > 299
		digitOfA := intFromString(sliceA[index])
		digitOfB := intFromString(sliceB[index])
		log.Println("Comparing at index:", index, "Elements: ", digitOfA, " & ", digitOfB)

		// Return results before end of permutation if possible
		if digitOfA > digitOfB {
			log.Println("RESULT:", "A>B")
			return ResultVersionAIsMoreThanVersionB
		} else if digitOfA < digitOfB {
			log.Println("RESULT:", "A<B")
			return ResultVersionAIsLessThanVersionB
		} else {
			log.Println("\tBoth are equal at index", index, ", resuming to next element...")
		}
	}
	log.Println("RESULT:", "A==B")
	return ResultVersionAIsEqualToVersionB
}

/**
intFromString Convert string to int
*/
func intFromString(stringToConvert string) int {
	parsedInteger, err := strconv.Atoi(stringToConvert)

	// Throw error if it exists
	if err != nil {
		log.Fatal("ERROR->", "Trouble found converting non-digit: ", stringToConvert)
	}
	return parsedInteger
}

/**
* compareSemVer some 'quick-usage' code I found on the library page
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
