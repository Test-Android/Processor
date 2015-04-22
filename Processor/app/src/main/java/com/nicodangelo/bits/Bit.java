package com.nicodangelo.bits;

public class Bit
{
    private long bits;
    private String bitType;

    public Bit()
    {
        bits = 0;
        bitType = "bit";
    }

    public Bit(long bits)
    {
        this.bits = bits;
    }

    public Bit(long bits, String bitType)
    {
        this.bits = bits;
        this.bitType = bitType;
    }

    public long getBits()
    {
        return bits;
    }

    public String getBitType()
    {
        return bitType;
    }

    public void setBits(long bits)
    {
        this.bits = bits;
    }

    public void setBitType(long bits)
    {
        if(bits < 8){bitType = "bit";}
        else if(bits >= 8 && bits < 1000){bitType = "byte";}
        else if(bits >= 1000 && bits < 8000){bitType = "kilobit";}
    }

    public void addBits(long bits)
    {
        this.bits += bits;
    }

    public void subtractBits(long bits)
    {
        this.bits -= bits;
    }
}
