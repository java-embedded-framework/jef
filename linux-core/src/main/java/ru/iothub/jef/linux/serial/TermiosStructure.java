 

package ru.iothub.jef.linux.serial;


/*
typedef unsigned char   cc_t;
typedef unsigned int    speed_t;
typedef unsigned int    tcflag_t;

#define NCCS 32
struct termios
  {
    tcflag_t c_iflag;
    tcflag_t c_oflag;
            tcflag_t c_cflag;
            tcflag_t c_lflag;
            cc_t c_line;
            cc_t c_cc[NCCS];
            speed_t c_ispeed;
            speed_t c_ospeed;
            #define _HAVE_STRUCT_TERMIOS_C_ISPEED 1
            #define _HAVE_STRUCT_TERMIOS_C_OSPEED 1
            };

*/

public class TermiosStructure {
    int c_iflag;		/* input mode flags */
    int  c_oflag;		/* output mode flags */
    int  c_cflag;		/* control mode flags */
    int  c_lflag;		/* local mode flags */
    byte  c_line;			/* line discipline */
    byte[] c_cc;		/* control characters */
    int  c_ispeed;		/* input speed */
    int  c_ospeed;		/* output speed */

    public TermiosStructure() {
    }

    public TermiosStructure(int c_iflag, int c_oflag, int c_cflag, int c_lflag, byte c_line, byte[] c_cc, int c_ispeed, int c_ospeed) {
        this.c_iflag = c_iflag;
        this.c_oflag = c_oflag;
        this.c_cflag = c_cflag;
        this.c_lflag = c_lflag;
        this.c_line = c_line;
        this.c_cc = c_cc;
        this.c_ispeed = c_ispeed;
        this.c_ospeed = c_ospeed;
    }

    public int getC_iflag() {
        return c_iflag;
    }

    public void setC_iflag(int c_iflag) {
        this.c_iflag = c_iflag;
    }

    public int getC_oflag() {
        return c_oflag;
    }

    public void setC_oflag(int c_oflag) {
        this.c_oflag = c_oflag;
    }

    public int getC_cflag() {
        return c_cflag;
    }

    public void setC_cflag(int c_cflag) {
        this.c_cflag = c_cflag;
    }

    public int getC_lflag() {
        return c_lflag;
    }

    public void setC_lflag(int c_lflag) {
        this.c_lflag = c_lflag;
    }

    public byte getC_line() {
        return c_line;
    }

    public void setC_line(byte c_line) {
        this.c_line = c_line;
    }

    public byte[] getC_cc() {
        return c_cc;
    }

    public void setC_cc(byte[] c_cc) {
        this.c_cc = c_cc;
    }

    public int getC_ispeed() {
        return c_ispeed;
    }

    public void setC_ispeed(int c_ispeed) {
        this.c_ispeed = c_ispeed;
    }

    public int getC_ospeed() {
        return c_ospeed;
    }

    public void setC_ospeed(int c_ospeed) {
        this.c_ospeed = c_ospeed;
    }
}
