package org.devdynamos.components;

import org.devdynamos.utils.Console;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.BeanProperty;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class DatePicker extends JPanel {
    public static int defaultHeight = 300;
    public static int defaultWidth = 300;

    private JFrame frame;
    private Date initialDate;
    private Date selectedDate;

    protected final Color BACKGROUND_COLOR = new Color(244, 244, 244);
    protected final Color FOREGROUND_COLOR = new Color(45, 45, 45);
    private final String INITIAL_DATE_FORMAT = "yyyy/MM/dd";
    protected final int FILL = 0;
    protected final int EMPTY = 1;

    public DatePicker(Date initialDate) {
        this.initialDate = initialDate;
        this.selectedDate = initialDate;

        initCalendar();
    }

    public JPanel getDatePickerPanel() {
        return this;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public String getSelectedDateString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(INITIAL_DATE_FORMAT);
        return dateFormatter.format(selectedDate);
    }

    public void showDialog() {
        frame = new JFrame("Date Picker");
        frame.setContentPane(this);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void nextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.initialDate);
        calendar.add(Calendar.MONTH, 1);

        this.initialDate = calendar.getTime();

        initCalendar();
        revalidate();

        if(frame == null) return;
        frame.setContentPane(this);
        frame.pack();
    }

    public void prevMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.initialDate);
        calendar.add(Calendar.MONTH, -1);

        this.initialDate = calendar.getTime();

        initCalendar();
        revalidate();

        if(frame == null) return;
        frame.setContentPane(this);
        frame.pack();
    }

    private void setSelectedDate(int date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.set(Calendar.DATE, date);

        selectedDate = calendar.getTime();
    }

    private void initCalendar() {
        removeAll();

//        Console.log(getMonthLength(initialDate));
//        Console.log(getMaxWeeks(initialDate));
//        Console.log(getFirstDatePosition(initialDate));

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(defaultWidth, defaultHeight));

        JPanel pnlContainer = new JPanel();
        pnlContainer.setLayout(new BorderLayout());
        pnlContainer.setBorder(BorderFactory.createEmptyBorder());
        pnlContainer.setBackground(BACKGROUND_COLOR);
        pnlContainer.setPreferredSize(new Dimension(defaultWidth, defaultHeight));

        JPanel pnlYearMonthContainer = new JPanel();
        pnlYearMonthContainer.setLayout(new FlowLayout());
        pnlYearMonthContainer.setBorder(BorderFactory.createEmptyBorder());

        JButton btnPrev = new JButton("<");
        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prevMonth();
            }
        });

        JLabel lblYear = new JLabel(String.valueOf(getYear(this.initialDate)), JLabel.CENTER);
        lblYear.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblMonth = new JLabel(getMonthText(this.initialDate), JLabel.CENTER);
        lblMonth.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton btnNext = new JButton(">");
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextMonth();
            }
        });

        pnlYearMonthContainer.add(btnPrev);
        pnlYearMonthContainer.add(lblYear);
        pnlYearMonthContainer.add(lblMonth);
        pnlYearMonthContainer.add(btnNext);

        pnlContainer.add(pnlYearMonthContainer, BorderLayout.NORTH);

        JPanel pnlCalenderContainer = new JPanel();
        pnlCalenderContainer.setLayout(new GridLayout(7, 7, 5, 5));
        pnlCalenderContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlCalenderContainer.setBackground(BACKGROUND_COLOR);

        String[] weekDays = { "Mo", "Tu", "We",  "Th", "Fr", "Sa", "Su" };
        for(String day : weekDays){
            JPanel pnlBlock = getWeekDayBlock(day);
            pnlCalenderContainer.add(pnlBlock);
        }
        int firstDatePosition = getFirstDatePosition(this.initialDate); // subtracting 1 here because we start a week by monday, and it is sunday by default;

        for (int i = 0; i < 42; i++) {
            int date = (i + 1) - firstDatePosition;
            if(i < firstDatePosition || date > getMonthLength(this.initialDate)){
                JPanel pnlEmptyBlock = new DatePicker.DateBlock(
                        date,
                        i,
                        EMPTY
                );
                pnlCalenderContainer.add(pnlEmptyBlock);
                continue;
            }
            JPanel pnlBlock = new DatePicker.DateBlock(
                    date,
                    i,
                    FILL,
                    (dateBlock, selected) -> {
                        for (Component component : pnlCalenderContainer.getComponents()){
                            if(component instanceof DateBlock){
                                ((DateBlock) component).setSelected(false);
                            }
                        }
                        dateBlock.setSelected(true);
                        setSelectedDate(date);
                    }
            );
            pnlCalenderContainer.add(pnlBlock);
        }

        pnlContainer.add(pnlCalenderContainer, BorderLayout.CENTER);

        add(pnlContainer);
    }

    private JPanel getDateBlock(int date, int position, int type){
        JPanel pnlBlock = new JPanel();
        pnlBlock.setLayout(new BorderLayout());
        pnlBlock.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        if(type == FILL)
            pnlBlock.setBackground(Color.WHITE);
        else
            pnlBlock.setBackground(BACKGROUND_COLOR);

        pnlBlock.setPreferredSize(new Dimension(40, 40));

        JLabel lblDate = new JLabel(String.valueOf(date), JLabel.CENTER);
        lblDate.setFont(new Font("Arial", Font.PLAIN, 16));

        int datePosition = (position + 1);
        if((datePosition + 1) % 7 == 0 || datePosition % 7 == 0){
            lblDate.setForeground(new Color(220, 76, 100));
        }else{
            lblDate.setForeground(new Color(45, 45, 45));
        }

        if(type == FILL)
            pnlBlock.add(lblDate, BorderLayout.CENTER);

        return pnlBlock;
    }

    private JPanel getWeekDayBlock(String day){
        JPanel pnlBlock = new JPanel();
        pnlBlock.setLayout(new BorderLayout());
        pnlBlock.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlBlock.setBackground(BACKGROUND_COLOR);
        pnlBlock.setPreferredSize(new Dimension(40, 40));

        JLabel lblDate = new JLabel(day, JLabel.CENTER);
        lblDate.setFont(new Font("Arial", Font.PLAIN, 16));
        if(day.equalsIgnoreCase("sa") || day.equalsIgnoreCase("su")){
            lblDate.setForeground(new Color(220, 76, 100));
        }else{
            lblDate.setForeground(new Color(45, 45, 45));
        }

        pnlBlock.add(lblDate, BorderLayout.CENTER);

        return pnlBlock;
    }

    private int getFirstDatePosition(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);

        int actualFirstDatePosition = calendar.get(Calendar.DAY_OF_WEEK);

//        Console.log("actual first date : " + actualFirstDatePosition);

        if(actualFirstDatePosition == 1)
            return  0; // return it as monday directly

        return actualFirstDatePosition - 2; // subtracting 1 make it represent as a value between [0-6] and subtracting another 1 make it starts from monday
    }

    private int getMaxWeeks(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(
                Calendar.DATE,
                calendar.getMaximum(Calendar.DATE) - 1 // subtracting 1 because it returns the first date from the next month as the max date
        );

        if(getMonthLength(this.initialDate) > 30)
            return calendar.get(Calendar.WEEK_OF_MONTH);

        return calendar.get(Calendar.WEEK_OF_MONTH) - 1;
    }

    private int getMonthLength(YearMonth yearMonth){
        return yearMonth.lengthOfMonth();
    }

    private int getMonthLength(String dateString){
        try{
            SimpleDateFormat dateFormatter = new SimpleDateFormat(INITIAL_DATE_FORMAT);
            Date date = dateFormatter.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
            return yearMonth.lengthOfMonth();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return -1;
    }

    private int getMonthLength(Date date){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
            return yearMonth.lengthOfMonth();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return -1;
    }

    private int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    private int getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH);
    }

    private int getDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DATE);
    }

    private String getMonthText(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
        return formatter.format(date);
    }

    private String toDateString(String date, @Nullable String format){
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                format == null ? INITIAL_DATE_FORMAT : format
        );
        return dateFormatter.format(date);
    }

    private String toDateString(Date date, @Nullable String format){
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                format == null ? INITIAL_DATE_FORMAT : format
        );
        return dateFormatter.format(date);
    }

    interface DateBlockOnClickCallback {
        void execute(DateBlock dateBlock, boolean selected);
    }

    class DateBlock extends JPanel {
        private final int date;
        private final int mode;
        private final int position;
        private final DateBlockOnClickCallback onClickCallback;
        private boolean selected = false;

        private final Color IDLE_COLOR = Color.WHITE;
        private final Color HOVER_COLOR = new Color(226, 236, 244);
        private final Color CLICKED_COLOR = new Color(90, 149, 204);
        private final Color SELECTED_COLOR = new Color(138, 178, 215);

        public DateBlock(int date, int position, int mode, DateBlockOnClickCallback onClickCallback){
            this.date = date;
            this.position = position;
            this.mode = mode;
            this.onClickCallback = onClickCallback;

            initComponent();
        }
        public DateBlock(int date, int position, int mode){
            this.date = date;
            this.position = position;
            this.mode = mode;
            this.onClickCallback = null;

            initComponent();
        }

        public void setSelected(boolean selected){
            if(selected){
                this.selected = true;

                if(mode != FILL) return;
                setBackground(SELECTED_COLOR);
            }else{
                this.selected = false;

                if(mode != FILL) return;
                setBackground(IDLE_COLOR);
            }
        }

        private boolean isSelectedEquals(int date){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(initialDate);
            calendar.set(Calendar.DATE, date);

            return selectedDate.equals(calendar.getTime());
        }

        private void onClick(){
            if(onClickCallback == null) return;
            onClickCallback.execute(this, selected);
        }

        private void initComponent() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            if(mode == FILL)
                setBackground(Color.WHITE);
            else
                setBackground(BACKGROUND_COLOR);

            setPreferredSize(new Dimension(40, 40));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onClick();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if(mode == FILL && !selected){
                        setBackground(CLICKED_COLOR);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(mode == FILL && !selected)
                        setBackground(SELECTED_COLOR);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if(mode == FILL && !selected)
                        setBackground(HOVER_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(mode == FILL && !selected)
                        setBackground(IDLE_COLOR);
                }
            });

            JLabel lblDate = new JLabel(String.valueOf(date), JLabel.CENTER);
            lblDate.setFont(new Font("Arial", Font.PLAIN, 16));

            int datePosition = (position + 1);
            if((datePosition + 1) % 7 == 0 || datePosition % 7 == 0){
                lblDate.setForeground(new Color(220, 76, 100));
            }else{
                lblDate.setForeground(new Color(45, 45, 45));
            }

            if(isSelectedEquals(date)){
                setSelected(true);
            }

            if(mode == FILL)
                add(lblDate, BorderLayout.CENTER);
        }
    }
}
