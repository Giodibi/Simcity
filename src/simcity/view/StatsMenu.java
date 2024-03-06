package simcity.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import simcity.model.GameEngine;
import simcity.model.Forest;
import simcity.model.Police;
import simcity.model.Road;
import simcity.model.Stadium;

public class StatsMenu extends JDialog {

    private JTabbedPane tabbedPane;
    private JPanel tab1;
    private JPanel tab2;
    private JPanel monetary;
    private JPanel cityStats;

    private JLabel income;
    private JSlider taxSlider;
    private LocalDate startDate;
    private LocalDate changedDate;
    private int tempTax;
    private JLabel tax;
    private JLabel taxPercent;
    private JLabel taxIncome;
    private JLabel money;
    private JLabel totalIncome;
    private JButton save;

    private JLabel expenses;
    private JLabel cityData;
    private JTable expensesTable;
    private JTable cityTable;

    private DefaultTableModel expensesTbModel;
    private DefaultTableModel cityTbModel;

    private JComboBox cityStatsPicker;

    public StatsMenu(GameEngine engine) {
        this.tabbedPane = new JTabbedPane();
        this.tab1 = new JPanel(new BorderLayout());
        this.tab2 = new JPanel(new BorderLayout());

        this.monetary = new JPanel();
        this.cityStats = new JPanel();

        this.income = new JLabel("Income");
        this.tax = new JLabel("Tax:");
        this.taxSlider = new JSlider(0, 100, engine.getRate());
        this.taxPercent = new JLabel(String.valueOf(taxSlider.getValue()) + " %");
        this.taxIncome = new JLabel("Tax Income:");
        this.money = new JLabel(engine.calcTaxIncome(engine.getRate()) + " $");
        this.totalIncome = new JLabel("Total Income: " + (engine.getIncome() - engine.getExpenses()) + " $");

        this.save = new JButton("Save");
        this.startDate = engine.getDate();
        this.changedDate = startDate;

        this.expenses = new JLabel("Expenses");
        this.cityData = new JLabel("City Data");

        String[] col = {"Type", "Upkeep Cost"};
        String[] cityCol = {"Type", "Avarage Bonus Happiness"};

        this.expensesTbModel = new DefaultTableModel(col, 10);
        this.expensesTable = new JTable(expensesTbModel);
        this.cityTbModel = new DefaultTableModel(cityCol, 10);
        this.cityTable = new JTable(cityTbModel);

        String[] statsOptions = {"Happiness", "Population"};
        this.cityStatsPicker = new JComboBox(statsOptions);

        setfirstTab(engine);
        setSecondTab(engine);

        tabbedPane.add("Income & Expenses", tab1);
        tabbedPane.add("City Stats", tab2);

        this.setPreferredSize(new Dimension(800, 800));
        this.setMinimumSize(new Dimension(800, 800));
        this.save.setSize(new Dimension(40, 30));

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.add(tabbedPane);

    }

    public void setfirstTab(GameEngine engine) {
        expensesTable.setEnabled(false);
        expensesTable.setRowHeight(50);

        income.setFont(new Font("arial", Font.PLAIN, 20));
        tax.setFont(new Font("arial", Font.PLAIN, 20));
        taxPercent.setFont(new Font("arial", Font.PLAIN, 20));
        taxIncome.setFont(new Font("arial", Font.PLAIN, 20));
        money.setFont(new Font("arial", Font.PLAIN, 20));
        expenses.setFont(new Font("arial", Font.PLAIN, 20));
        save.setFont(new Font("arial", Font.PLAIN, 20));
        totalIncome.setFont(new Font("arial", Font.PLAIN, 20));

        monetary.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();

        c1.gridx = 1;
        c1.gridy = 0;
        c1.ipady = 40;

        monetary.add(income, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.ipady = 40;

        monetary.add(tax, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 1;
        c3.gridy = 1;
        c3.ipady = 40;

        monetary.add(taxSlider, c3);

        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 2;
        c4.gridy = 1;
        c4.ipady = 10;
        monetary.add(taxPercent, c4);

        GridBagConstraints c5 = new GridBagConstraints();
        c5.gridx = 3;
        c5.gridy = 1;
        c5.ipady = 40;
        monetary.add(save, c5);

        GridBagConstraints c6 = new GridBagConstraints();
        c6.gridx = 0;
        c6.gridy = 2;
        c6.ipady = 40;

        monetary.add(taxIncome, c6);

        GridBagConstraints c7 = new GridBagConstraints();
        c7.gridx = 1;
        c7.gridy = 2;
        c7.ipady = 40;
        c7.ipadx = 40;

        monetary.add(money, c7);

        GridBagConstraints c8 = new GridBagConstraints();
        c8.gridx = 1;
        c8.gridy = 3;
        c8.ipady = 40;

        monetary.add(expenses, c8);

        GridBagConstraints c9 = new GridBagConstraints();
        c9.fill = GridBagConstraints.HORIZONTAL;
        c9.gridx = 0;
        c9.gridy = 4;
        c9.gridwidth = 4;

        monetary.add(add(new JScrollPane(expensesTable)), c9);

        GridBagConstraints c10 = new GridBagConstraints();
        c10.fill = GridBagConstraints.HORIZONTAL;
        c10.gridx = 1;
        c10.gridy = 5;
        c10.ipady = 40;

        monetary.add(totalIncome, c10);

        taxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                tempTax = source.getValue();
                taxPercent.setText(String.valueOf(source.getValue()) + " %");

                money.setText(engine.calcTaxIncome(tempTax) + " $");
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (changedDate == startDate) {
                    refreshYearlyTaxes(engine);

                } else {
                    LocalDate currentDate = engine.getDate();
                    Period period = changedDate.until(currentDate);
                    int yearsBetween = period.between(changedDate, currentDate).getYears();
                    if (yearsBetween >= 1) {
                        refreshYearlyTaxes(engine);

                    }
                }
                taxSlider.setEnabled(false);
                save.setEnabled(false);

                changedDate = engine.getDate();

            }
        });

        refreshTable(engine);
        tab1.add(BorderLayout.NORTH, monetary);

    }

    private void refreshYearlyTaxes(GameEngine engine) {
        engine.setTaxRate(tempTax);
        engine.calculateExpenses();
        engine.calculateIncome();
        totalIncome.setText("Total Income: " + (engine.getIncome() - engine.getExpenses()) + " $");
    }

    public void canChangeTaxes(GameEngine engine) {
        if (changedDate.until(engine.getDate()).getYears() >= 1) {
            taxSlider.setEnabled(true);
            save.setEnabled(true);
        }
    }

    public void refreshTable(GameEngine engine) {
        int fSum = 0;
        int pSum = 0;
        int sSum = 0;
        int rSum = 0;

        for (Sprite[] sprites : engine.getMap().getMap_array()) {
            for (Sprite s : sprites) {
                String type = s.getClass().getSimpleName();
                switch (type) {
                    case "Forest":
                        fSum += ((Forest) s).getFee();
                        break;
                    case "Police":
                        pSum += ((Police) s).getFee();
                        break;
                    case "Stadium":
                        sSum += ((Stadium) s).getFee();
                        break;
                    case "Road":
                        rSum += ((Road) s).getFee();
                        break;
                    default:
                        break;
                }
            }
        }
        ArrayList<String> tags = new ArrayList<>(Arrays.asList("Forests", "Police Stations", "Stadiums", "Roads"));
        ArrayList<Integer> sums = new ArrayList<>(Arrays.asList(fSum, pSum, sSum, rSum));
        this.expensesTbModel.setRowCount(0);
        for (int i = 0; i < 4; i++) {
            Object[] row = {tags.get(i), String.valueOf(sums.get(i)) + " $"};
            this.expensesTbModel.addRow(row);
        }
        Object[] total = {"Total Expenses: ", (fSum + pSum + sSum + rSum) + " $"};
        this.expensesTbModel.addRow(total);
        totalIncome.setText("Total Income: " + (engine.getIncome() - engine.getExpenses()) + " $");
        money.setText(engine.getIncome() + " $");

    }

    private void setSecondTab(GameEngine engine) {
        cityTable.setEnabled(false);
        cityTable.setRowHeight(50);

        cityData.setFont(new Font("arial", Font.PLAIN, 20));

        cityStats.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 1;
        c1.gridy = 0;
        c1.ipady = 40;

        cityStats.add(cityData, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.gridy = 1;
        c2.ipady = 20;

        cityStats.add(cityStatsPicker, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 1;
        c3.gridy = 2;
        c3.ipady = 40;

        JScrollPane sp = new JScrollPane(cityTable);

        cityStatsPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshSecondTable(engine);
            }
        });

        cityStats.add(add(sp), c3);

        tab2.add(BorderLayout.NORTH, cityStats);
    }

    public void refreshSecondTable(GameEngine engine) {
        cityTbModel.setRowCount(0);
        if (cityStatsPicker.getSelectedItem().toString().equals("Population")) {
            Object[] incoming = {"Incoming: ", engine.getNewCitizenNum() > 0 ? engine.getNewCitizenNum() : 0};
            Object[] leaving = {"Leaving: ", engine.getLeavingCitNum() > 0 ? engine.getLeavingCitNum() : 0};
            Object[] total = {"Total Change: ", engine.getNewCitizenNum() - engine.getLeavingCitNum()};

            cityTbModel.addRow(incoming);
            cityTbModel.addRow(leaving);
            cityTbModel.addRow(total);
        } else {
            Object[] police = {"Police Stations:", engine.getHappinessModifiersSum()[0]};
            Object[] forest = {"Stadiums: ", engine.getHappinessModifiersSum()[1]};
            Object[] stadium = {"Forests:", engine.getHappinessModifiersSum()[2]};
            Object[] total = {"Total Bonus Happiness: ", engine.getHappinessModifiersSum()[3]};
            cityTbModel.addRow(police);
            cityTbModel.addRow(stadium);
            cityTbModel.addRow(forest);
            cityTbModel.addRow(total);
        }

    }

    public JSlider getTaxSlider() {
        return this.taxSlider;
    }

    public void setTaxValue(int n) {
        this.taxSlider.setValue(n);
    }

    public JLabel getTaxPercent() {
        return this.taxPercent;
    }

    public void setCityStatsPickerIndex(int n) {
        this.cityStatsPicker.setSelectedIndex(n);
    }

    public Object getCityStatsPickerSelected() {
        return this.cityStatsPicker.getSelectedItem();
    }

    public JButton getSave() {
        return this.save;
    }

}
