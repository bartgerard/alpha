package be.gerard.robot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
public final class Camera {

// https://github.com/KonradIT/goprowifihack/blob/master/HERO6/HERO6-Commands.md

    @RequiredArgsConstructor
    @Getter
    public enum Mode {
        VIDEO(0),
        PHOTO(1),
        MULTI_SHOT(2);

        private final int code;
    }

    @RequiredArgsConstructor
    @Getter
    public enum SubMode {
        VIDEO(Mode.VIDEO, 0),
        VIDEO_TIMELAPSE(Mode.VIDEO, 1),
        VIDEO_PHOTO(Mode.VIDEO, 2),
        VIDEO_LOOPING(Mode.VIDEO, 3),
        PHOTO_SINGLE(Mode.PHOTO, 1),
        PHOTO_NIGHT(Mode.PHOTO, 2),
        MULTI_SHOT_BURST(Mode.MULTI_SHOT, 0),
        MULTI_SHOT_TIMELAPSE(Mode.MULTI_SHOT, 1),
        MULTI_SHOT_NIGHT_LAPSE(Mode.MULTI_SHOT, 2);

        private final Mode mode;
        private final int subCode;
    }

    public interface Control {
    }

    @RequiredArgsConstructor
    public static final class CaptureMode {

        @RequiredArgsConstructor
        public static final class Video {

            public enum FrameRate {

            }

        }

        @RequiredArgsConstructor
        public static final class Photo {

        }

    }

    public static class Shutter implements Control {

        @RequiredArgsConstructor
        @Getter
        public enum Action {
            TRIGGER(1),
            STOP(0);

            private final int code;
        }

    }

}
