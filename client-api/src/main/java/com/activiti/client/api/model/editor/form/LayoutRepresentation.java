/*
 *  Copyright (C) 2005-2015 Alfresco Software Limited.
 *
 * This file is part of Alfresco Activiti Mobile for Android.
 *
 * Alfresco Activiti Mobile for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco Activiti Mobile for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.activiti.client.api.model.editor.form;

/**
 * @author jbarrez
 */
public class LayoutRepresentation
{

    private Integer row;

    private Integer column;

    private Integer colspan;

    public LayoutRepresentation()
    {
    }

    public LayoutRepresentation(Integer row, Integer column, Integer colspan)
    {
        this.row = row;
        this.column = column;
        this.colspan = colspan;
    }

    public Integer getRow()
    {
        return row;
    }

    public void setRow(Integer row)
    {
        this.row = row;
    }

    public Integer getColumn()
    {
        return column;
    }

    public void setColumn(Integer column)
    {
        this.column = column;
    }

    public Integer getColspan()
    {
        return colspan;
    }

    public void setColspan(Integer colspan)
    {
        this.colspan = colspan;
    }

}
