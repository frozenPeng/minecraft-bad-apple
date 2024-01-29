import cv2
import os

def save_frames(video_path, output_folder, resize_width=None, resize_height=None):
    # Open the video file
    cap = cv2.VideoCapture(video_path)
    if not cap.isOpened():
        print("Error: Unable to open video file")
        return

    # Get video properties
    frame_count = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    fps = int(cap.get(cv2.CAP_PROP_FPS))
    width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))

    print(f"Total frames: {frame_count}")

    # Loop through each frame and save
    for i in range(frame_count):
        ret, frame = cap.read()
        if not ret:
            print("Error: Unable to read frame")
            break

        # Resize frame if required
        if resize_width and resize_height:
            frame = cv2.resize(frame, (resize_width, resize_height))

        # Save frame
        frame_name = f"frame_{i}.jpg"  # Adjust file extension as needed
        frame_path = os.path.join(output_folder, frame_name)
        cv2.imwrite(frame_path, frame)

        print(f"Saved frame {i+1}/{frame_count}")

    cap.release()
    print("Frames saved successfully")

# Example usage
video_path = "./bad_apple.mp4"  # Provide path to your video file
output_folder = "./bad_apple"  # Output folder where frames will be saved
resize_width = 160  # Adjust width as needed
resize_height = 120  # Adjust height as needed
save_frames(video_path, output_folder, resize_width, resize_height)
