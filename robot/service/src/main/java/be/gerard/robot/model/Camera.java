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

    @FunctionalInterface
    public interface Setting {
        String getCode();

        default String toUrl() {
            return String.format(
                    "/gpControl/setting/%s",
                    getCode()
            );
        }
    }

    public interface Control {
    }

    @RequiredArgsConstructor
    public static final class CaptureMode {

        @RequiredArgsConstructor
        public static final class Video {

            @RequiredArgsConstructor
            @Getter
            public enum ProTune implements Setting {
                OFF("10/0"),
                ON("10/1");

                private final String code;
            }

            public enum FrameRate {

            }

        }

        @RequiredArgsConstructor
        public static final class Photo {

            @RequiredArgsConstructor
            @Getter
            public enum Resolution implements Setting {
                _12MP_WIDE("17/0"),
                _12MP_LINEAR("17/10"),
                _12MP_MEDIUM("17/8"),
                _12MP_NARROW("17/9");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum ProTune implements Setting {
                OFF("21/0"),
                ON("21/1");

                private final String code;

            }

            @RequiredArgsConstructor
            @Getter
            public enum EV implements Setting {
                _PLUS_2("26/0"),
                _PLUS_1_5("26/1"),
                _PLUS_1("26/2"),
                _PLUS_0_5("26/3"),
                _0("26/4"),
                _MIN_0_5("26/5"),
                _MIN_1("26/6"),
                _MIN_1_5("26/7"),
                _MIN_2("26/8");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum ProTuneShutter implements Setting {
                AUTO("97/0"),
                _1_125("97/1"),
                _1_250("97/2"),
                _1_500("97/3"),
                _1_1000("97/4"),
                _1_2000("97/5");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum RAW implements Setting {
                ON("82/1"),
                OFF("82/0");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum HDR implements Setting {
                ON("100/1"),
                OFF("100/0");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum WhiteBalance implements Setting {
                AUTO("22/0"),
                _3000K("22/1"),
                _2300K("22/8"),
                _2800K("22/9"),
                _3200K("22/10"),
                _4000K("22/5"),
                _4500K("22/11"),
                _4800K("22/6"),
                _5500K("22/2"),
                _6000K("22/7"),
                _6500K("22/3"),
                NATIVE("22/4");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum Color implements Setting {
                GO_PRO("23/0"),
                FLAT("23/1");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum Min implements Setting {
                HIGH("25/0"),
                MED("25/1"),
                LOW("25/2");

                private final String code;
            }

            public static final class ISO {

                @RequiredArgsConstructor
                @Getter
                public enum Limit implements Setting {
                    _800("24/0"),
                    _400("24/1"),
                    _200("24/2"),
                    _100("24/3");

                    private final String code;
                }

                @RequiredArgsConstructor
                @Getter
                public enum Min implements Setting {
                    _800("75/0"),
                    _400("75/1"),
                    _200("75/2"),
                    _100("75/3");

                    private final String code;
                }

            }

            public static final class Night {

                @RequiredArgsConstructor
                @Getter
                public enum ExposureTime implements Setting {
                    AUTO("19/0"),
                    _2("19/1"),
                    _5("19/2"),
                    _10("19/3"),
                    _15("19/4"),
                    _20("19/5"),
                    _30("19/6");

                    private final String code;
                }

                @RequiredArgsConstructor
                @Getter
                public enum RAW implements Setting {
                    ON("98/1"),
                    OFF("98/0");

                    private final String code;
                }

            }

        }

    }

    @NoArgsConstructor
    public static final class Shutter implements Control {

        @RequiredArgsConstructor
        @Getter
        public enum Action {
            TRIGGER(1),
            STOP(0);

            private final int code;
        }

    }

    @NoArgsConstructor
    public static final class General {

        @RequiredArgsConstructor
        @Getter
        public enum Orientation implements Setting {
            UP("52/1"),
            DOWN("52/2"),
            GYRO("52/0");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum QuickCapture implements Setting {
            ON("54/1"),
            OFF("54/0");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum VoiceControl implements Setting {
            ON("86/1"),
            OFF("86/0");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum LED implements Setting {
            OFF("91/0"),
            ON("91/2"),
            FRONT_OFF("/91/1");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum Language implements Setting {
            ENGLISH("84/0"),
            CHINESE("84/1"),
            GERMAN("84/2"),
            ITALIAN("84/3"),
            SPANISH("84/4"),
            JAPANESE("84/5"),
            FRENCH("84/6");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum VoiceControlLanguage implements Setting {
            ENGLISH_US("85/0"),
            ENGLISH_UK("85/1"),
            ENGLISH_AUS("85/2"),
            GERMAN("85/3"),
            FRENCH("85/4"),
            ITALIAN("85/5"),
            SPANISH("85/6"),
            SPANISH_NA("85/7"),
            CHINESE("85/8"),
            JAPANESE("85/9");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum Beeps implements Setting {
            OFF("56/2"),
            _0_7("56/1"),
            FULL("56/0");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum VideoFormat implements Setting {
            NTSC("57/0"),
            PAL("57/1");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum AutoOff implements Setting {
            NEVER("59/0"),
            _1M("59/1"),
            _2M("59/2"),
            _3M("59/3"),
            _5M("59/4");

            private final String code;
        }

        @RequiredArgsConstructor
        @Getter
        public enum AutoLockScreen implements Setting {
            _7_SEC("103/5"),
            OFF("103/3");

            private final String code;
        }

        @NoArgsConstructor
        public static final class GPS {

            @RequiredArgsConstructor
            @Getter
            public enum Tag implements Setting {
                ON("83/1"),
                OFF("83/0");

                private final String code;
            }

        }

        @NoArgsConstructor
        public static final class LCD {

            @RequiredArgsConstructor
            @Getter
            public enum Display implements Setting {
                ON("72/1"),
                OFF("72/0");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum ScreenDisplay implements Setting {
                ON("58/1"),
                OFF("58/0");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum Brightness implements Setting {
                HIGH("49/0"),
                MEDIUM("49/1"),
                LOW("49/2");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum Lock implements Setting {
                ON("50/1"),
                OFF("50/0");

                private final String code;
            }

            @RequiredArgsConstructor
            @Getter
            public enum Timeout implements Setting {
                LCD_NEVER_SLEEP("51/0"),
                LCD_1MIN_SLEEP_TIMEOUT("51/1"),
                LCD_2MIN_SLEEP_TIMEOUT("51/2"),
                LCD_3MIN_SLEEP_TIMEOUT("51/3");

                private final String code;
            }

        }


        // Turn on by saying "GoPro Turn On":
        // ON: http://10.5.5.9/gp/gpControl/setting/104/1
        // OFF: http://10.5.5.9/gp/gpControl/setting/104/0
        // Set date and time
        // http://10.5.5.9/gp/gpControl/command/setup/date_time?p=%11%0b%10%11%29%2c
        // The hex string at the end is the same as for HERO3, so in the example: 11 = (20)17, 0b = 11 (November), 10 = 16, 11 = 17, 29 = 41, 2c = 44. Example bash code for date string, see https://github.com/ztzhang/GoProWifiCommand/issues/3.
        // Streaming tweaks:
        // Stream BitRate :
        // Supports any number ( like 7000000), but limited by wifi throughput, packet loss and video glitches may appear. Correct parameter ID is 62!
        //         250 Kbps: http://10.5.5.9/gp/gpControl/setting/62/250000
        //         400 Kbps: http://10.5.5.9/gp/gpControl/setting/62/400000
        //         600 Kbps: http://10.5.5.9/gp/gpControl/setting/62/600000
        //         700 Kbps: http://10.5.5.9/gp/gpControl/setting/62/700000
        //         800 Kbps: http://10.5.5.9/gp/gpControl/setting/62/800000
        //         1 Mbps: http://10.5.5.9/gp/gpControl/setting/62/1000000
        //         1.2 Mbps: http://10.5.5.9/gp/gpControl/setting/62/1200000
        //         1.6 Mbps: http://10.5.5.9/gp/gpControl/setting/62/1600000
        //         2 Mbps: http://10.5.5.9/gp/gpControl/setting/62/2000000
        //         2.4 Mbps: http://10.5.5.9/gp/gpControl/setting/62/2400000
        // Stream Window Size:
        // Sizes with 720 height are tested on Hero 5 Black.
        //         Default: http://10.5.5.9/gp/gpControl/setting/64/0
        //         240: http://10.5.5.9/gp/gpControl/setting/64/1
        //         240, 3:4: http://10.5.5.9/gp/gpControl/setting/64/2
        //         240 1:2: http://10.5.5.9/gp/gpControl/setting/64/3
        //         480: http://10.5.5.9/gp/gpControl/setting/64/4
        //         480 3:4: http://10.5.5.9/gp/gpControl/setting/64/5
        //         480 1:2: http://10.5.5.9/gp/gpControl/setting/64/6
        //         720 (1280x720) : http://10.5.5.9/gp/gpControl/setting/64/7
        //         720 3:4 (960x720) http://10.5.5.9/gp/gpControl/setting/64/8
        //         720 1:2 (640x720) http://10.5.5.9/gp/gpControl/setting/64/9
        // WiFi AP Settings:
        // Turn WiFi OFF: http://10.5.5.9/gp/gpControl/setting/63/0
        // Switch WiFi to App/Smartphone mode: http://10.5.5.9/gp/gpControl/setting/63/1
        // Switch WiFi to GoPro RC: http://10.5.5.9/gp/gpControl/setting/63/2
        // Switch WiFi to GoPro Smart Remote RC: http://10.5.5.9/gp/gpControl/setting/63/4
    }

}
