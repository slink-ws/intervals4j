package ws.slink.intervals;

/**
 * Shift-aware `Year` implementation of Interval interface
 */
public class ShiftedYear extends ShiftedInterval {

    private ShiftedYear(Interval year, String shift) {
        super(year, shift);
    }

    public static ShiftedYear of(Year year, String shift) {
        return new ShiftedYear(year, shift);
    }
    private static ShiftedYear create(Interval year, String shift) {
        return new ShiftedYear(year, shift);
    }

    @Override
    public Interval withPrevious() {
        Interval previous = this.getBase().previous();
        return ShiftedYear.create(
            new IntervalBuilder()
                .start(previous.getStart())
                .end(this.base.getEnd())
                .timezone(this.base.timezone())
                .build(),
            this.shift
        );
    }

}
