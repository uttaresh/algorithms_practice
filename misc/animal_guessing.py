# list of animals, sorted by increasing order of size
animals = ["mouse", "squirrel", "rabbit", "cat", "dog", "wolf", "tiger", "hippo", "elephant"]


def guess():
    print("Let's try to guess what animal you're thinking of!")
    start = 0
    end = len(animals)
    while (end >= start):
        mid = int((start + end) / 2)
        guess = animals[mid]
        correct = None
        while correct != "yes" and correct != "no":
            correct = input(f"Were you thinking of a {guess}?").lower()
        if correct == "yes":
            print("Nice! I knew I could guess what you were thinking!")
            return
        else:
            relatively = None
            while relatively != "larger" and relatively != "smaller":
                relatively = input(f"Hmmm... was animal larger or smaller than my guess, {guess}?").lower()
            if relatively == "larger":
                start = mid + 1
            elif relatively == "smaller":
                end = mid - 1
            else:
                raise ValueError("Must say higher or lower")
    print("Hmmm... looks like I wasn't able to guess what you were thinking :(")

guess()
