/*
 * This file is generated by jOOQ.
*/
package com.distribution.pojo.tables;


import com.distribution.pojo.Distribution;
import com.distribution.pojo.Indexes;
import com.distribution.pojo.Keys;
import com.distribution.pojo.tables.records.SysRoleMenuRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * ?????????
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysRoleMenu extends TableImpl<SysRoleMenuRecord> {

    private static final long serialVersionUID = 1866485456;

    /**
     * The reference instance of <code>distribution.sys_role_menu</code>
     */
    public static final SysRoleMenu SYS_ROLE_MENU = new SysRoleMenu();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SysRoleMenuRecord> getRecordType() {
        return SysRoleMenuRecord.class;
    }

    /**
     * The column <code>distribution.sys_role_menu.id</code>.
     */
    public final TableField<SysRoleMenuRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>distribution.sys_role_menu.role_id</code>. 角色ID
     */
    public final TableField<SysRoleMenuRecord, Long> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.BIGINT, this, "角色ID");

    /**
     * The column <code>distribution.sys_role_menu.menu_id</code>. 菜单ID
     */
    public final TableField<SysRoleMenuRecord, Long> MENU_ID = createField("menu_id", org.jooq.impl.SQLDataType.BIGINT, this, "菜单ID");

    /**
     * Create a <code>distribution.sys_role_menu</code> table reference
     */
    public SysRoleMenu() {
        this(DSL.name("sys_role_menu"), null);
    }

    /**
     * Create an aliased <code>distribution.sys_role_menu</code> table reference
     */
    public SysRoleMenu(String alias) {
        this(DSL.name(alias), SYS_ROLE_MENU);
    }

    /**
     * Create an aliased <code>distribution.sys_role_menu</code> table reference
     */
    public SysRoleMenu(Name alias) {
        this(alias, SYS_ROLE_MENU);
    }

    private SysRoleMenu(Name alias, Table<SysRoleMenuRecord> aliased) {
        this(alias, aliased, null);
    }

    private SysRoleMenu(Name alias, Table<SysRoleMenuRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "?????????");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Distribution.DISTRIBUTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SYS_ROLE_MENU_PRIMARY, Indexes.SYS_ROLE_MENU_SYS_ROLE_MENU_MENU_ID_INDEX, Indexes.SYS_ROLE_MENU_SYS_ROLE_MENU_ROLE_ID_INDEX, Indexes.SYS_ROLE_MENU_SYS_ROLE_MENU_ROLE_ID_INDEX_1, Indexes.SYS_ROLE_MENU_SYS_ROLE_MENU__INDEX_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<SysRoleMenuRecord, Long> getIdentity() {
        return Keys.IDENTITY_SYS_ROLE_MENU;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SysRoleMenuRecord> getPrimaryKey() {
        return Keys.KEY_SYS_ROLE_MENU_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SysRoleMenuRecord>> getKeys() {
        return Arrays.<UniqueKey<SysRoleMenuRecord>>asList(Keys.KEY_SYS_ROLE_MENU_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleMenu as(String alias) {
        return new SysRoleMenu(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRoleMenu as(Name alias) {
        return new SysRoleMenu(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SysRoleMenu rename(String name) {
        return new SysRoleMenu(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SysRoleMenu rename(Name name) {
        return new SysRoleMenu(name, null);
    }
}
