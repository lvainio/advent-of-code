from PIL import Image

image = Image.open("days.jpg")

width, height = image.size
cell_width = width // 5
cell_height = height // 5

day = 1
for j in range(5):
    for i in range(5): 
        left = i * cell_width
        top = j * cell_height
        right = (i + 1) * cell_width
        bottom = (j + 1) * cell_height

        cell_image = image.crop((left, top, right, bottom))
        filename = f"day_{day}.jpg"
        cell_image.save(filename)
        day += 1